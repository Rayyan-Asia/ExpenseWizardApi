package Rayyan.Asia.ExpenseWizard.application.configurations;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public GroupedOpenApi controllerApi() {
        return GroupedOpenApi.builder()
                .group("controller-api")
                .packagesToScan("Rayyan.Asia.ExpenseWizard.representation.controllers")
                .build();
    }
}