package com.poc.techvoice.userservice.application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Value("${app.host}")
    private String host;

    @Bean
    public OpenAPI springShopOpenAPI() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(getMicroserviceInfo());
        Server server = new Server();
        server.setUrl(host);
        openAPI.setServers(Collections.singletonList(server));
        return openAPI;
    }

    private Info getMicroserviceInfo() {
        return new Info()
                .title("User Management Service")
                .description("Welcome to Tech Voice Online Content Publishing Platform. " +
                        "This service handles user authentication related functions")
                .version("1.0")
                .license(new License()
                        .name("MIT License")
                        .url("https://mit-license.org/")
                );
    }
}
