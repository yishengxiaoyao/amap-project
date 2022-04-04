package com.edu.amp.exception;

/**
 * @author apple
 */
public class FsmException extends RuntimeException{
    private String message;

    public FsmException(String message) {
        this.message = message;
    }
}
