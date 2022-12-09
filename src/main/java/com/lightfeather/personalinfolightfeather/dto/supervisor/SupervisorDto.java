package com.lightfeather.personalinfolightfeather.dto.supervisor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.lightfeather.personalinfolightfeather.domain.model.Supervisor} entity
 */
@Data
@AllArgsConstructor
@Builder
public class SupervisorDto implements Serializable {
    private final String id;
    private final String phone;
    private final String jurisdiction;
    private final String identificationNumber;
    private final String firstName;
    private final String lastName;
}
