package com.lightfeather.personalinfolightfeather.domain.service;

import com.lightfeather.personalinfolightfeather.domain.model.Supervisor;
import com.lightfeather.personalinfolightfeather.domain.repository.SupervisorRepository;
import com.lightfeather.personalinfolightfeather.dto.supervisor.SupervisorDto;
import com.lightfeather.personalinfolightfeather.mapper.SupervisorDataMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

class SupervisorServiceTest {
    @InjectMocks
    private SupervisorService sut;
    @Mock
    private SupervisorRepository supervisorRepository;
    @Mock
    private SupervisorDataMapper supervisorDataMapper;
    private AutoCloseable closeable;
    private List<Supervisor> supervisors;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        supervisors = List.of(
                Supervisor.builder()
                        .firstName("Karson")
                        .lastName("Olson")
                        .phone("204-798-9969")
                        .identificationNumber("d4900a18-a304-42c6-a8e5-a6c8c3f17bc0")
                        .jurisdiction("u")
                        .build(),
                Supervisor.builder()
                        .firstName("Robbie")
                        .lastName("Heller")
                        .phone("792.910.1754")
                        .identificationNumber("96188a56-1f92-4876-8df3-d8761ea5162f")
                        .jurisdiction("9")
                        .build()
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void persistSupervisors() {
        List<SupervisorDto> dtos = List.of(
                SupervisorDto.builder()
                        .firstName("Karson")
                        .lastName("Olson")
                        .phone("204-798-9969")
                        .identificationNumber("d4900a18-a304-42c6-a8e5-a6c8c3f17bc0")
                        .jurisdiction("u")
                        .build()

        );
        Mockito.when(supervisorRepository.saveAll(ArgumentMatchers.anyCollection()))
                .thenReturn(supervisors);
        Mockito.when(supervisorDataMapper.supervisorsDtoToSupervisors(ArgumentMatchers.any()))
                .thenReturn(supervisors);
        sut.persistSupervisors(dtos);
        Mockito.verify(supervisorRepository, Mockito.times(1))
                .saveAll(ArgumentMatchers.anyCollection());
    }

    @Test
    void shouldGetSortedAndFormattedAndOmitNumericSupervisors() {
        Mockito.when(supervisorRepository.findAll()).thenReturn(supervisors);
        List<String> formattedSupervisors = sut.getSortedAndFormattedSupervisors();
        Assertions.assertEquals(1, formattedSupervisors.size());
    }
}
