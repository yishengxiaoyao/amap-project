package com.edu.amp.event;

/**
 * 订单状态迁移事件
 * @author apple
 */
public interface OrderStateEvent {
    /**
     * 订单状态事件
     * @return
     */
    String getEventType();

    /**
     * 订单ID
     * @return
     */
    String getOrderId();

    /**
     * 如果orderState不为空，则代表只有订单是当前状态才进行迁移
     * @return
     */
    default String orderState() {
        return null;
    }

    /**
     * 是否要新创建订单
     * @return
     */
    boolean newCreate();
}