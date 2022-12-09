package com.lightfeather.personalinfolightfeather.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.lightfeather.personalinfolightfeather.IntegrationTest;
import com.lightfeather.personalinfolightfeather.domain.service.SupervisorService;
import com.lightfeather.personalinfolightfeather.dto.supervisor.SupervisorDto;
import com.lightfeather.personalinfolightfeather.fixture.SupervisorFixture;
import com.lightfeather.personalinfolightfeather.helper.SupervisorRestClientHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

@AutoConfigureMockMvc
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SupervisorControllerIT extends IntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WireMockServer mockServer;
    @Autowired
    private SupervisorRestClientHelper supervisorRestClientHelper;
    @Autowired
    private SupervisorService supervisorService;

    @BeforeAll
    void beforeAll() {
        mockServer.stubFor(get("/managers")
                .willReturn(aResponse().withBody(SupervisorFixture.getSupervisorsJsonResponse())
                        .withHeader("Content-Type", "application/json")
                        .withStatus(HttpStatus.OK.value())));
        mockServer.start();
        List<SupervisorDto> supervisorDtos = supervisorRestClientHelper.fetchSupervisors();
        supervisorService.persistSupervisors(supervisorDtos);
    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        mockServer.stop();
    }

    @Test
    void shouldGetSortedAndFormattedAndOmitNumericSupervisorsAndReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/supervisors")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]")
                        .value("u - Olson, Karson"))
                .andDo(mvcResult -> log.info("result: {}", mvcResult.getResponse().getContentAsString()));
    }
}
