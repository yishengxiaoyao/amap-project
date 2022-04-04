package com.edu.amp.fsm;

import com.edu.amp.domain.ServiceResult;
import com.edu.amp.event.OrderStateEvent;
import com.edu.amp.service.FsmOrder;

/**
 * 状态机执行引擎
 *
 * @author apple
 */
public interface OrderFsmEngine {

    /**
     * 执行状态迁移事件，不传FsmOrder默认会根据orderId从FsmOrderService接口获取
     *
     * @param orderStateEvent
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent) throws Exception;

    /**
     * 执行状态迁移事件，可携带FsmOrder参数
     *
     * @param orderStateEvent
     * @param fsmOrder
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent, FsmOrder fsmOrder) throws Exception;
}