package com.java.stcassessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	private String code;

	public NotFoundException(ErrorCode errorCode) {
		super(errorCode.getDescription());
		code = errorCode.getCode();
	}

	public NotFoundException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
