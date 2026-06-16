package com.example.be12th.global.error.exception;

import lombok.Getter;

@Getter
public class App12thException extends RuntimeException {
    private final ErrorCode errorCode;
    public App12thException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public App12thException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
    }

    public App12thException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getErrorMessage(), cause);
        this.errorCode = errorCode;
    }
}
