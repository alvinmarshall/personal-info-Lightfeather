package com.lightfeather.personalinfolightfeather.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class FeignExceptionMessage implements Serializable {
    private String message;
}
