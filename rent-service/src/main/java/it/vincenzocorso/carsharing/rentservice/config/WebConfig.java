package it.vincenzocorso.carsharing.rentservice.config;

import it.vincenzocorso.carsharing.common.spring.web.DefaultExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DefaultExceptionHandler.class)
public class WebConfig {
}
