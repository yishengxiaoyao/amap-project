package com.edu.amp.service;

/**
 * 状态机引擎所需的订单信息基类信息
 * @author apple
 */
public interface FsmOrder {

    /**
     * 订单ID
     * @return
     */
    String getOrderId();

    /**
     * 订单状态
     * @return
     */
    String getOrderState();

    /**
     * 订单的业务属性
     * @return
     */
    String bizCode();

    /**
     * 订单的场景属性
     * @return
     */
    String sceneId();
}