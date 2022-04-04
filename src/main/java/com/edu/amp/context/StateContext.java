package com.edu.amp.context;

import com.edu.amp.event.OrderStateEvent;
import com.edu.amp.service.FsmOrder;

/**
 * 状态上下文
 *
 * @author apple
 */
public class StateContext<C> {
    private String state;
    private String args;
    /**
     * 订单操作事件
     */
    private OrderStateEvent orderStateEvent;
    /**
     * 状态机需要的订单基本信息
     */
    private FsmOrder fsmOrder;
    /**
     * 业务可定义的上下文泛型对象
     */
    private C context;

    public StateContext(OrderStateEvent orderStateEvent, FsmOrder fsmOrder) {
        this.orderStateEvent = orderStateEvent;
        this.fsmOrder = fsmOrder;
    }

    public OrderStateEvent getOrderStateEvent() {
        return orderStateEvent;
    }

    public void setOrderStateEvent(OrderStateEvent orderStateEvent) {
        this.orderStateEvent = orderStateEvent;
    }

    public C getContext() {
        return context;
    }

    public void setContext(C context) {
        this.context = context;
    }

    public FsmOrder getFsmOrder() {
        return fsmOrder;
    }

    public void setFsmOrder(FsmOrder fsmOrder) {
        this.fsmOrder = fsmOrder;
    }
}
