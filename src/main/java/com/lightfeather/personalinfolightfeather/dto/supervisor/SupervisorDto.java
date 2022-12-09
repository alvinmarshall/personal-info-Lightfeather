package com.lightfeather.personalinfolightfeather.dto.supervisor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link com.lightfeather.personalinfolightfeather.domain.model.Supervisor} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupervisorDto implements Serializable {
    private long id;
    private String phone;
    private String jurisdiction;
    private String identificationNumber;
    private String firstName;
    private String lastName;
}
