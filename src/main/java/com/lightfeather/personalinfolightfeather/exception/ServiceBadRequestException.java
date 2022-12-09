package com.lightfeather.personalinfolightfeather.exception;

import com.lightfeather.personalinfolightfeather.dto.ApiError;

import java.util.List;

public class ServiceBadRequestException extends CustomException {
    public ServiceBadRequestException(ExceptionTypes serviceBadRequest, String url, List<ApiError> errors) {
        super(serviceBadRequest, url, errors);
    }
}
