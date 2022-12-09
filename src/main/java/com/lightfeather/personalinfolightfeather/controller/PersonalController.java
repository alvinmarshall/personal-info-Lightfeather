package com.lightfeather.personalinfolightfeather.controller;

import com.lightfeather.personalinfolightfeather.dto.ApiResponseData;
import com.lightfeather.personalinfolightfeather.dto.personal.PersonalDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api")
@Slf4j
public class PersonalController {
    @PostMapping("submit")
    public ResponseEntity<?> personal(@RequestBody @Valid PersonalDto input) {
        ApiResponseData<?> apiResponseData = ApiResponseData.builder().data(input).build();
        log.info("personal info: {}", input);
        return new ResponseEntity<>(apiResponseData, HttpStatus.ACCEPTED);
    }
}
