package com.lightfeather.personalinfolightfeather;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = PersonalInfoLightFeatherApplication.class)
@ActiveProfiles("test")
public class IntegrationTest {

}
