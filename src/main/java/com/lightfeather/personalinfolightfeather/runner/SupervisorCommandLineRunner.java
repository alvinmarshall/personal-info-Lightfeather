package com.lightfeather.personalinfolightfeather.runner;

import com.lightfeather.personalinfolightfeather.domain.service.SupervisorService;
import com.lightfeather.personalinfolightfeather.dto.supervisor.SupervisorDto;
import com.lightfeather.personalinfolightfeather.helper.SupervisorRestClientHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("!test")
@Component
@Slf4j
public class SupervisorCommandLineRunner implements CommandLineRunner {
    private final BackgroundTaskService backgroundTaskService;
    private final SupervisorRestClientHelper supervisorRestClientHelper;
    private final SupervisorService supervisorService;

    public SupervisorCommandLineRunner(
            BackgroundTaskService backgroundTaskService,
            SupervisorRestClientHelper supervisorRestClientHelper,
            SupervisorService supervisorService) {
        this.backgroundTaskService = backgroundTaskService;
        this.supervisorRestClientHelper = supervisorRestClientHelper;
        this.supervisorService = supervisorService;
    }

    @Override
    public void run(String... args) {
        log.info("Try seeding supervisors");
        if (supervisorService.checkIfEmpty()) {
            log.warn("supervisors already exist");
            return;
        }
        backgroundTaskService.execute(() -> {
            try {
                List<SupervisorDto> supervisors = supervisorRestClientHelper.fetchSupervisors();
                supervisorService.persistSupervisors(supervisors);
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
