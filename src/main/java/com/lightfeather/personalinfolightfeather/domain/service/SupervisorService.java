package com.lightfeather.personalinfolightfeather.domain.service;

import com.lightfeather.personalinfolightfeather.domain.model.Supervisor;
import com.lightfeather.personalinfolightfeather.domain.repository.SupervisorRepository;
import com.lightfeather.personalinfolightfeather.dto.supervisor.SupervisorDto;
import com.lightfeather.personalinfolightfeather.mapper.SupervisorDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SupervisorService {
    private final SupervisorRepository supervisorRepository;
    private final SupervisorDataMapper supervisorDataMapper;


    public SupervisorService(SupervisorRepository supervisorRepository, SupervisorDataMapper supervisorDataMapper) {
        this.supervisorRepository = supervisorRepository;
        this.supervisorDataMapper = supervisorDataMapper;
    }


    public void persistSupervisors(List<SupervisorDto> input) {
        log.info("saving supervisors");
        List<Supervisor> supervisors = supervisorDataMapper.supervisorsDtoToSupervisors(input);
        supervisorRepository.saveAll(supervisors);
        log.info("supervisors saved with size: {}", supervisors.size());
    }

    public List<String> getSortedAndFormattedSupervisors() {
        Comparator<Supervisor> supervisorComparator = getSupervisorComparator();
        return supervisorRepository.findAll().stream()
                .filter(supervisor -> !isNumeric(supervisor.getJurisdiction()))
                .sorted(supervisorComparator)
                .map(supervisor -> String.format("%s - %s, %s",
                                supervisor.getJurisdiction(),
                                supervisor.getLastName(),
                                supervisor.getFirstName()
                        )
                )
                .collect(Collectors.toList());
    }

    public List<String> getSortedAndFormattedSupervisors(Sort sort) {
        return supervisorRepository.findAll(sort).stream()
                .filter(supervisor -> !isNumeric(supervisor.getJurisdiction()))
                .map(supervisor -> String.format("%s - %s, %s",
                                supervisor.getJurisdiction(),
                                supervisor.getLastName(),
                                supervisor.getFirstName()
                        )
                )
                .collect(Collectors.toList());
    }

    private Comparator<Supervisor> getSupervisorComparator() {
        Comparator<Supervisor> compareByJurisdiction = Comparator.comparing(Supervisor::getJurisdiction);
        Comparator<Supervisor> compareByLastName = Comparator.comparing(Supervisor::getLastName);
        Comparator<Supervisor> compareByFirstName = Comparator.comparing(Supervisor::getFirstName);
        return compareByJurisdiction
                .thenComparing(compareByLastName)
                .thenComparing(compareByFirstName);
    }

    public boolean checkIfEmpty() {
        return supervisorRepository.count() > 0;
    }

    private boolean isNumeric(String input) {
        Pattern numericPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (ObjectUtils.isEmpty(input)) return false;
        return numericPattern.matcher(input).matches();
    }


}
