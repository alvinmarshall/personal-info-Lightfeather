package com.lightfeather.personalinfolightfeather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightfeather.personalinfolightfeather.IntegrationTest;
import com.lightfeather.personalinfolightfeather.dto.personal.PersonalDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@Slf4j
class PersonalControllerIT extends IntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private PersonalDto personalDto;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        personalDto = PersonalDto.builder()
                .firstName("John")
                .lastName("Ownes")
                .email("john@me.com")
                .phoneNumber("1209348934")
                .supervisor("u")
                .build();
    }

    @AfterEach
    void tearDown() {


    }

    @Test
    void shouldSubmitValidPersonalInfoAndReturn202() throws Exception {
        String dto = mapper.writeValueAsString(personalDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dto)
                ).andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andDo(mvcResult -> log.info("result: {}", mvcResult.getResponse().getContentAsString()));
    }

    void shouldSubmitPersonalInfoIfEmailIsNullAndReturn202() throws Exception {
        personalDto.setEmail(null);
        String dto = mapper.writeValueAsString(personalDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dto)
                ).andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andDo(mvcResult -> log.info("result: {}", mvcResult.getResponse().getContentAsString()));
    }

    void shouldSubmitPersonalInfoIfPhoneNumberIsNullAndReturn202() throws Exception {
        personalDto.setEmail(null);
        String dto = mapper.writeValueAsString(personalDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dto)
                ).andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andDo(mvcResult -> log.info("result: {}", mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void shouldNotSubmitIfFirstNameIsNullAndReturn400() throws Exception {
        personalDto.setFirstName(null);
        String dto = mapper.writeValueAsString(personalDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dto)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message")
                        .value("firstName: This is a required parameters!"))
                .andDo(mvcResult -> log.info("result: {}", mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void shouldNotSubmitIfLastNameIsNullAndReturn400() throws Exception {
        personalDto.setLastName(null);
        String dto = mapper.writeValueAsString(personalDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dto)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message")
                        .value("lastName: This is a required parameter!"))
                .andDo(mvcResult -> log.info("result: {}", mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void shouldNotSubmitIfSupervisorIsNullAndReturn400() throws Exception {
        personalDto.setSupervisor(null);
        String dto = mapper.writeValueAsString(personalDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dto)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message")
                        .value("supervisor: This is a required parameter!"))
                .andDo(mvcResult -> log.info("result: {}", mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void shouldNotSubmitIfAllRequiredFieldsAreNullAndReturn400() throws Exception {
        personalDto.setFirstName(null);
        personalDto.setLastName(null);
        personalDto.setSupervisor(null);
        String dto = mapper.writeValueAsString(personalDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dto)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.length()").value(3))
                .andDo(mvcResult -> log.info("result: {}", mvcResult.getResponse().getContentAsString()));
    }
}
