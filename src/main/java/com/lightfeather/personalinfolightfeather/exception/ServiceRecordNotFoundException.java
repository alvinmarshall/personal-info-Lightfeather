package com.lightfeather.personalinfolightfeather.exception;

import com.lightfeather.personalinfolightfeather.dto.ApiError;

import java.util.List;

public class ServiceRecordNotFoundException extends CustomException{

    public ServiceRecordNotFoundException(ExceptionTypes recordNotFound, String url, List<ApiError> errors) {
        super(recordNotFound, url, errors);
    }
}
