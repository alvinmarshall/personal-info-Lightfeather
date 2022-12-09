package com.lightfeather.personalinfolightfeather.helper;

import com.lightfeather.personalinfolightfeather.client.SupervisorRestClient;
import com.lightfeather.personalinfolightfeather.dto.supervisor.SupervisorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SupervisorRestClientHelper {
    private final SupervisorRestClient supervisorRestClient;

    public SupervisorRestClientHelper(SupervisorRestClient supervisorRestClient) {
        this.supervisorRestClient = supervisorRestClient;
    }

    public List<SupervisorDto> fetchSupervisors() {
        log.info("fetching supervisors");
        ResponseEntity<List<SupervisorDto>> response = supervisorRestClient.getSupervisors();
        return response.getBody();
    }
}
