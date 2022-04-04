package com.edu.amp.domain;

import lombok.Data;

/**
 * @author apple
 */
@Data
public class ServiceResult<T> {
    private boolean success = true;
    private String message;
    private String orderId;
    public ServiceResult(String orderId, String message) {
        this.orderId = orderId;
        this.message = message;
    }

    public ServiceResult() {

    }
}
