package com.dev.essentials.springbootessentials.exception;

/**
 * Created by Samuel on 28/04/18.
 */
public class CustomExceptionType {

    private String errorMessage;

    public CustomExceptionType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
