package com.edu.amp.context;

import com.edu.amp.domain.OrderInfo;

/**
 * @author apple
 */
public class CreateOrderContext {
    private OrderInfo orderInfo;
    private String estimatePriceInfo;

    public String getEstimatePriceInfo() {
        return estimatePriceInfo;
    }

    public void setEstimatePriceInfo(String estimatePriceInfo) {
        this.estimatePriceInfo = estimatePriceInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
}
