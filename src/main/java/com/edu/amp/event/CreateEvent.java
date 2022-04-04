package com.edu.amp.event;

import lombok.Getter;

/**
 * @author apple
 */
@Getter
public class CreateEvent implements OrderStateEvent {
    private String eventType;
    private String orderId;
    private String userId;
    private String orderState;

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public boolean newCreate() {
        return true;
    }

    public CreateEvent(String eventType, String orderId, String userId, String orderState) {
        this.eventType = eventType;
        this.orderId = orderId;
        this.userId = userId;
        this.orderState = orderState;
    }

}
