package com.lightfeather.personalinfolightfeather.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightfeather.personalinfolightfeather.dto.ApiError;
import com.lightfeather.personalinfolightfeather.exception.ExceptionTypes;
import com.lightfeather.personalinfolightfeather.exception.ServiceBadRequestException;
import com.lightfeather.personalinfolightfeather.exception.ServiceDownException;
import com.lightfeather.personalinfolightfeather.exception.ServiceRecordNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper mapper;
    private final ErrorDecoder errorDecoder = new Default();

    public FeignErrorDecoder(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            InputStream inputStream = response.body().asInputStream();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            FeignExceptionMessage feignExceptionMessage = mapper.readValue(inputStream, FeignExceptionMessage.class);
            List<ApiError> errors = parseErrors(feignExceptionMessage);
            return switch (response.status()) {
                case 400 -> new ServiceBadRequestException(
                        ExceptionTypes.SERVICE_BAD_REQUEST,
                        response.request().url(),
                        errors);

                case 404 -> new ServiceRecordNotFoundException(
                        ExceptionTypes.RECORD_NOT_FOUND,
                        response.request().url(),
                        errors);
                case 500, 504 -> new ServiceDownException(
                        ExceptionTypes.SERVICE_DOWN,
                        response.request().url(),
                        errors);
                default -> errorDecoder.decode(methodKey, response);
            };
        } catch (IOException e) {
            log.error("unable to read response stream", e);
        }
        return errorDecoder.decode(methodKey, response);
    }

    private List<ApiError> parseErrors(FeignExceptionMessage responseBody) {
        List<ApiError> apiErrors = new ArrayList<>();
        if (!ObjectUtils.isEmpty(responseBody.getMessage())) {
            apiErrors.add(ApiError.builder().message(responseBody.getMessage()).build());
        }
        return apiErrors;
    }
}
