package com.lightfeather.personalinfolightfeather.config;



import com.lightfeather.personalinfolightfeather.dto.ApiError;
import com.lightfeather.personalinfolightfeather.dto.ApiResponseData;
import com.lightfeather.personalinfolightfeather.exception.CustomException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionAdviser extends ResponseEntityExceptionHandler {
    //    400
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logError(ex.getClass().getName());
        final List<ApiError> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(ApiError.builder().message(error.getField() + ": " + error.getDefaultMessage()).build());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(ApiError.builder().message(error.getObjectName() + ": " + error.getDefaultMessage()).build());
        }

        ApiResponseData<?> response = ApiResponseData.builder()
                .success(false)
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logError(ex.getClass().getName());
        final List<ApiError> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(ApiError.builder().message(error.getField() + ": " + error.getDefaultMessage()).build());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(ApiError.builder().message(error.getObjectName() + ": " + error.getDefaultMessage()).build());
        }
        ApiResponseData<?> response = ApiResponseData.builder()
                .success(false)
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logError(ex.getClass().getName());
        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
        List<ApiError> errors = List.of(ApiError.builder().message(error).build());
        ApiResponseData<?> response = ApiResponseData.builder()
                .success(false)
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logError(ex.getClass().getName());
        final String error = ex.getRequestPartName() + " part is missing";
        List<ApiError> errors = List.of(ApiError.builder().message(error).build());
        ApiResponseData<?> response = ApiResponseData.builder()
                .success(false)
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logError(ex.getClass().getName());
        final String error = ex.getParameterName() + " parameter is missing";
        List<ApiError> errors = List.of(ApiError.builder().message(error).build());
        ApiResponseData<?> response = ApiResponseData.builder()
                .success(false)
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        logError(ex.getClass().getName());
        final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        List<ApiError> errors = List.of(ApiError.builder().message(error).build());
        ApiResponseData<?> response = ApiResponseData.builder()
                .success(false)
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        logError(ex.getClass().getName());
        final List<ApiError> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(
                    ApiError.builder()
                            .message(violation.getRootBeanClass().getName() + " " +
                                    violation.getPropertyPath() + ": " + violation.getMessage())
                            .build());
        }
        ApiResponseData<?> response = ApiResponseData.builder()
                .success(false)
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // 404
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logError(ex.getClass().getName());
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        List<ApiError> errors = List.of(ApiError.builder().message(error).build());
        ApiResponseData<?> response = ApiResponseData.builder()
                .success(false)
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    // 405
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logError(ex.getClass().getName());
        final StringBuilder error = new StringBuilder();
        error.append(ex.getMethod());
        error.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> error.append(t).append(" "));
        List<ApiError> errors = List.of(ApiError.builder().message(error.toString()).build());
        ApiResponseData<?> response = ApiResponseData.builder()
                .success(false)
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 415
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logError(ex.getClass().getName());
        final StringBuilder error = new StringBuilder();
        error.append(ex.getContentType());
        error.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> error.append(t).append(" "));
        List<ApiError> errors = List.of(ApiError.builder().message(error.toString()).build());
        ApiResponseData<?> response = ApiResponseData.builder()
                .success(false)
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    // 500
    @ExceptionHandler({Exception.class, NullPointerException.class})
    public ResponseEntity<?> handleAll(final Exception ex, final WebRequest request) {
        logError(ex.getClass().getName());
        logger.error("error", ex);
        String error = "Application encountered an error";
        if (!ObjectUtils.isEmpty(ex.getMessage())) {
            error = ex.getLocalizedMessage();
        }
        List<ApiError> errors = List.of(ApiError.builder().message(error).build());
        ApiResponseData<?> response = ApiResponseData.builder()
                .success(false)
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<?> handleCustomExceptions(CustomException ex) {
        logger.info(ex.getReferenceId());
        logger.info(ex.getExceptionTypes());
        ApiResponseData<?> response = ApiResponseData.builder()
                .success(false)
                .errors(ex.getErrors())
                .build();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        switch (ex.getExceptionTypes()) {
            case SERVICE_DOWN -> status = HttpStatus.INTERNAL_SERVER_ERROR;
            case SERVICE_TIMEOUT -> status = HttpStatus.REQUEST_TIMEOUT;
            case RECORD_NOT_FOUND -> status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    private void logError(String name) {
        logger.info(name);
    }

}
