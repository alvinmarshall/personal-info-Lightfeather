package com.lightfeather.personalinfolightfeather.controller;

import com.lightfeather.personalinfolightfeather.domain.service.SupervisorService;
import com.lightfeather.personalinfolightfeather.dto.ApiResponseData;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class SupervisorController {
    private final SupervisorService supervisorService;

    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    @GetMapping("supervisors")
    public ResponseEntity<?> getSupervisors() {
        Sort orders = Sort.by(Sort.Direction.ASC, "jurisdiction", "lastName", "firstName");
        List<String> supervisors = supervisorService.getSortedAndFormattedSupervisors(orders);
        ApiResponseData<?> apiResponseData = ApiResponseData.builder().data(supervisors).build();
        return ResponseEntity.ok(apiResponseData);
    }
}
