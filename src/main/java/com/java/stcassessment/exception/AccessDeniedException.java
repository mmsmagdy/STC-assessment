package com.java.stcassessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AccessDeniedException extends RuntimeException {

    private String code;

    public AccessDeniedException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        code = errorCode.getCode();
    }

    public AccessDeniedException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
