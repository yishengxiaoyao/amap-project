package com.edu.amp.service;

import com.edu.amp.check.Checkable;
import com.edu.amp.check.CheckerExecutor;
import com.edu.amp.context.StateContext;
import com.edu.amp.domain.ServiceResult;
import com.edu.amp.plugin.PluginExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 状态机处理器模板类
 * @author apple
 */
@Component
public abstract class AbstractStateProcessor<T,C> implements StateProcessor<T,C>,StateActionStep<T,C> {
    @Resource
    private CheckerExecutor checkerExecutor;
    @Autowired
    private PluginExecutor pluginExecutor;
    @Override
    public final ServiceResult<T> action(StateContext<C> context) throws Exception {
        ServiceResult<T> result;
        Checkable checkable = this.getCheckable(context);
        try {
            //参数校验器
            result = checkerExecutor.serialCheck(checkable.getParamChecker(), context);
            if (!result.isSuccess()){
                return result;
            }
            //数据准备
            this.prepare(context);
            //串型校验器
            result = checkerExecutor.serialCheck(checkable.getSyncChecker(), context);
            if (!result.isSuccess()){
                return result;
            }
            //并行校验器
            result = checkerExecutor.parallelCheck(checkable.getAsyncChecker(), context);
            if (!result.isSuccess()){
                return result;
            }
            // getNextState不能在prepare前，因为有的nextState是根据prepare中的数据转换而来
            String nextState = this.getNextState(context);
            // 业务逻辑
            result = this.action(nextState, context);
            if (!result.isSuccess()) {
                return result;
            }
            // 在action和save之间执行插件逻辑
            this.pluginExecutor.parallelExecutor(context);
            // 持久化
            result = this.save(nextState, context);
            if (!result.isSuccess()) {
                return result;
            }
            // after
            this.after(context);
            return result;
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 指定过滤条件
     * @param context
     * @return
     */
    public abstract boolean filter(StateContext<?> context);
}
