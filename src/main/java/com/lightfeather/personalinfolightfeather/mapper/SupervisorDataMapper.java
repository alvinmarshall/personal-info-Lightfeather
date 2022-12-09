package com.lightfeather.personalinfolightfeather.mapper;

import com.lightfeather.personalinfolightfeather.domain.model.Supervisor;
import com.lightfeather.personalinfolightfeather.dto.supervisor.SupervisorDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SupervisorDataMapper {
    public List<Supervisor> supervisorsDtoToSupervisors(List<SupervisorDto> input) {
        return input.stream().map(this::supervisorDtoToSupervisor).collect(Collectors.toList());
    }

    public Supervisor supervisorDtoToSupervisor(SupervisorDto input) {
        Supervisor supervisor = Supervisor.builder().build();
        BeanUtils.copyProperties(input, supervisor);
        return supervisor;
    }


}
