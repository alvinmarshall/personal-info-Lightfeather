package com.lightfeather.personalinfolightfeather.exception;

import com.lightfeather.personalinfolightfeather.dto.ApiError;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private ExceptionTypes exceptionTypes;
    private String referenceId;
    private List<ApiError> errors;

    public CustomException(String message) {
        super(message);
        this.errors = List.of(ApiError.builder().message(message).build());
        this.exceptionTypes = ExceptionTypes.SERVICE_BAD_REQUEST;
    }

    public CustomException(String message, ExceptionTypes exceptionType) {
        super(message);
        this.errors = List.of(ApiError.builder().message(message).build());
        this.exceptionTypes = exceptionType;
    }

    public CustomException(ExceptionTypes exceptionTypes, String referenceId, List<ApiError> errors) {
        this.exceptionTypes = exceptionTypes;
        this.referenceId = referenceId;
        this.errors = errors;
    }
}
