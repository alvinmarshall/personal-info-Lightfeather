package com.lightfeather.personalinfolightfeather.dto.personal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalDto implements Serializable {
    @NotEmpty(message = "This is a required parameters!")
    private String firstName;
    @NotEmpty(message = "This is a required parameter!")
    private String lastName;
    private String email;
    private String phoneNumber;
    @NotEmpty(message = "This is a required parameter!")
    private String supervisor;
}
