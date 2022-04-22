package com.doyouknowdeway.sportsequipmentrent.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {
        "com.doyouknowdeway.sportsequipmentrent.model.entity",
        "com.doyouknowdeway.sportsequipmentrent.converter"
})
@EnableJpaRepositories(basePackages = "com.doyouknowdeway.sportsequipmentrent.repository")
public class DatabaseConfiguration {
}
