package com.lightfeather.personalinfolightfeather.client;

import com.lightfeather.personalinfolightfeather.dto.supervisor.SupervisorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "supervisorRestClient", url = "${client.supervisor_baseurl}")
public interface SupervisorRestClient {
    @GetMapping("managers")
    ResponseEntity<List<SupervisorDto>> getSupervisors();
}
