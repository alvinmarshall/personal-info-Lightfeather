package com.lightfeather.personalinfolightfeather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PersonalInfoLightFeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalInfoLightFeatherApplication.class, args);
    }

}
