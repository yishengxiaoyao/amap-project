package com.edu.amp.service;

import com.edu.amp.check.Checkable;
import com.edu.amp.context.StateContext;
import com.edu.amp.domain.ServiceResult;

/**
 * 状态迁移动作处理步骤
 * 发现每一个订单状态流转的流程中,都会有三个流程：校验、业务逻辑执行、数据更新持久化;于是再次抽象,
 * 可以将一个状态流转分为数据准备（prepare）——>校验（check）——>获取下一个状态（getNextState）
 * ——>业务逻辑执行（action）——>数据持久化（save）——>后续处理（after）这六个阶段;
 *
 * @author apple
 */
public interface StateActionStep<T, C> {
    /**
     * 获取检查器
     *
     * @param context
     * @return
     */
    Checkable getCheckable(StateContext<C> context);

    /**
     * 准备数据
     *
     * @param context
     */
    default void prepare(StateContext<C> context) {
    }

    /**
     * 校验
     *
     * @param context
     * @return
     */
    ServiceResult<T> check(StateContext<C> context);

    /**
     * 获取当前状态处理器处理完毕后，所处于的下一个状态
     *
     * @param context
     * @return
     */
    String getNextState(StateContext<C> context);

    /**
     * 状态动作方法，主要状态迁移逻辑
     *
     * @param nextState
     * @param context
     * @return
     * @throws Exception
     */
    ServiceResult<T> action(String nextState, StateContext<C> context) throws Exception;

    /**
     * 状态数据持久化
     *
     * @param nextState
     * @param context
     * @return
     * @throws Exception
     */
    ServiceResult<T> save(String nextState, StateContext<C> context) throws Exception;

    /**
     * 状态迁移成功，持久化后执行的后续处理
     *
     * @param context
     */
    void after(StateContext<C> context);
}
