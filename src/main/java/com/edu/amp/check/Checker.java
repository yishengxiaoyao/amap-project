package com.edu.amp.check;

import com.edu.amp.context.StateContext;
import com.edu.amp.domain.ServiceResult;

/**
 * 状态机校验器
 * @author apple
 */
public interface Checker<T, C> {
    /**
     * 校验参数
     * @param context
     * @return
     */
    ServiceResult<T> check(StateContext<C> context);

    /**
     * 多个checker时的执行顺序,默认为0
     * @return
     */
    default int order() {
        return 0;
    }

    /**
     * 是否需求release
     */
    default boolean needRelease() {
        return false;
    }
    /**
     * 业务执行完成后的释放方法,
     * 比如有些业务会在checker中加一些状态操作，等业务执行完成后根据结果选择处理这些状态操作,
     * 最典型的就是checker中加一把锁，release根据结果释放锁.
     */
    default void release(StateContext<C> context, ServiceResult<T> result) {
    }
}