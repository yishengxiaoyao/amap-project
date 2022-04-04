package com.edu.amp.domain;

import com.edu.amp.service.FsmOrder;
import lombok.Data;

/**
 * @author apple
 */
@Data
public class OrderInfo implements FsmOrder {
    private String orderState;
    private String orderId;
    private String userId;
    private String sceneId;
    private String bizCode;
    private String serviceType;

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public String getOrderState() {
        return orderState;
    }

    @Override
    public String bizCode() {
        return bizCode;
    }

    @Override
    public String sceneId() {
        return sceneId;
    }

    public String getUserId() {
        return userId;
    }
}
