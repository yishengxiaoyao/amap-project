package com.edu.amp.service;

import com.edu.amp.context.StateContext;
import com.edu.amp.domain.ServiceResult;

/**
 * 状态机处理器接口
 * @author apple
 */
public interface StateProcessor<T,C> {
    /**
     * 执行状态迁移的入口
     * @param context
     * @return
     * @throws Exception
     */
    ServiceResult<T> action(StateContext<C> context) throws Exception;
}