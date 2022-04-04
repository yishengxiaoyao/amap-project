package com.edu.amp.check;

import com.edu.amp.context.StateContext;
import com.edu.amp.domain.ServiceResult;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

/**
 * 校验器的执行器
 * @author apple
 */
@Slf4j
@Component
public class CheckerExecutor {
    /**
     * 定义线程名称
     */
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("check-executor-%d").build();
    /**
     * 初始化线程池
     */
    private static final ExecutorService executor = new ThreadPoolExecutor(5, 20, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100), namedThreadFactory);
    /**
     * 执行并行校验器，
     * 按照任务投递的顺序判断返回。
     */
    public <T, C> ServiceResult<T> parallelCheck(List<Checker> checkers, StateContext<C> context) {
        if (!CollectionUtils.isEmpty(checkers)) {
            if (checkers.size() == 1) {
                return checkers.get(0).check(context);
            }
            List<Future<ServiceResult>> resultList = Collections.synchronizedList(new ArrayList<>(checkers.size()));
            checkers.sort(Comparator.comparingInt(Checker::order));
            for (Checker c : checkers) {
                Future<ServiceResult> future = executor.submit(() -> c.check(context));
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
        }
        return new ServiceResult<>();
    }

    /**
     * 线程检查
     * @param checkers
     * @param context
     * @param <T>
     * @param <C>
     * @return
     */
    public <T, C> ServiceResult<T> serialCheck(List<Checker> checkers, StateContext<C> context) {
        if (!CollectionUtils.isEmpty(checkers)) {
            if (checkers.size() == 1) {
                return checkers.get(0).check(context);
            }
            checkers.sort(Comparator.comparingInt(Checker::order));
            for (Checker c : checkers) {
                ServiceResult<T> serviceResult = c.check(context);
                if (!serviceResult.isSuccess()){
                    return serviceResult;
                }
            }

        }
        return new ServiceResult<>();
    }

    /**
     * 执行checker的释放操作
     * @param checkable
     * @param context
     * @param result
     * @param <T>
     * @param <C>
     */
    public <T, C> void releaseCheck(Checkable checkable, StateContext<C> context, ServiceResult<T> result) {
        List<Checker> checkers = new ArrayList<>();
        checkers.addAll(checkable.getParamChecker());
        checkers.addAll(checkable.getSyncChecker());
        checkers.addAll(checkable.getAsyncChecker());
        checkers.removeIf(Checker::needRelease);
        if (!CollectionUtils.isEmpty(checkers)) {
            if (checkers.size() == 1) {
                checkers.get(0).release(context, result);
                return;
            }
            CountDownLatch latch = new CountDownLatch(checkers.size());
            for (Checker c : checkers) {
                executor.execute(() -> {
                    try {
                        c.release(context, result);
                    } finally {
                        latch.countDown();
                    }
                });
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}