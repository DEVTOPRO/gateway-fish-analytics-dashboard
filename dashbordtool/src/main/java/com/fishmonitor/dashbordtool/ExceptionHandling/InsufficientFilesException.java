package com.fishmonitor.dashbordtool.ExceptionHandling;

public class InsufficientFilesException extends RuntimeException {
    public InsufficientFilesException(String message) {
        super(message);
    }
}