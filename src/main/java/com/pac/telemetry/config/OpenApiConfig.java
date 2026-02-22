package com.pac.telemetry.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EV Telemetry API")
                        .version("1.0")
                        .description("ESP device telemetry ingestion and command queue API"))
                .addSecurityItem(new SecurityRequirement().addList("DeviceToken"))
                .addSecurityItem(new SecurityRequirement().addList("AdminKey"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("DeviceToken", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("X-Device-Token"))
                        .addSecuritySchemes("AdminKey", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("X-Admin-Key")));
    }
}
