package com.fishmonitor.dashbordtool.ExceptionHandling;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String message) {
        super(message);
    }
}