package com.cct.exception;

public class BadRequestException extends RuntimeException {
    private ErrorInfo errorInfo;

    public BadRequestException(ErrorInfo errorInfo) {
        super(errorInfo.getMessage());
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }
}
