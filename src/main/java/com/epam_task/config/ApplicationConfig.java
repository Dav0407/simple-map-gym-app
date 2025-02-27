package com.epam_task.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.epam_task")
@PropertySource("classpath:application.properties")
public class ApplicationConfig {
}
