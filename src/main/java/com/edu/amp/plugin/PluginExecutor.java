package com.edu.amp.plugin;

import com.edu.amp.check.Checker;
import com.edu.amp.constant.ErrorCodeEnum;
import com.edu.amp.context.StateContext;
import com.edu.amp.domain.ServiceResult;
import com.edu.amp.event.OrderStateEvent;
import com.edu.amp.exception.FsmException;
import com.edu.amp.postprocessor.DefaultPluginHandlerRegistry;
import com.edu.amp.service.AbstractStateProcessor;
import com.edu.amp.service.FsmOrder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.*;


/**
 * @author apple
 */
@Component
@Slf4j
public class PluginExecutor {

    /**
     * 定义线程名称
     */
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("plugin-executor-%d").build();
    /**
     * 初始化线程池
     */
    private static final ExecutorService executor = new ThreadPoolExecutor(5, 20, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100), namedThreadFactory);

    @Autowired
    private DefaultPluginHandlerRegistry defaultPluginHandlerRegistry;

    /**
     * 并行执行插件
     *
     * @param context
     * @param <T>
     * @param <C>
     * @return
     */
    public <T, C> ServiceResult<T> parallelExecutor(StateContext<C> context) {
        OrderStateEvent stateEvent = context.getOrderStateEvent();
        FsmOrder fsmOrder = context.getFsmOrder();
        // 根据状态+事件对象获取所对应的业务处理器集合
        List<PluginHandler> pluginHandlerList = defaultPluginHandlerRegistry.acquirePluginHandler(fsmOrder.getOrderState(),
                stateEvent.getEventType(), fsmOrder.bizCode(), fsmOrder.sceneId());

        List<Future<ServiceResult>> resultList = Collections.synchronizedList(new ArrayList<>(pluginHandlerList.size()));
        for (PluginHandler pluginHandler : pluginHandlerList) {
            Future<ServiceResult> future = executor.submit(() -> pluginHandler.action(context));
            resultList.add(future);
        }
        for (Future<ServiceResult> future : resultList) {
            try {
                ServiceResult sr = future.get();
                if (!sr.isSuccess()) {
                    return sr;
                }
            } catch (Exception e) {
                log.error("parallelCheck executor.submit error.", e);
                throw new RuntimeException(e);
            }
        }
        return new ServiceResult<>();
    }
}
