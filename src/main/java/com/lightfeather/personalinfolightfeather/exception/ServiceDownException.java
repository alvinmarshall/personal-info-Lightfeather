package com.lightfeather.personalinfolightfeather.exception;

import com.lightfeather.personalinfolightfeather.dto.ApiError;

import java.util.List;

public class ServiceDownException extends CustomException{

    public ServiceDownException(ExceptionTypes serviceDown, String url, List<ApiError> errors) {
        super(serviceDown, url, errors);
    }
}
