package com.lightfeather.personalinfolightfeather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseData<T> implements Serializable {
    @Builder.Default
    private boolean success = true;
    private T data;
    private List<ApiError> errors;

}
