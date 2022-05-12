package com.alterra.miniproject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName("Felix Reinaldo");
        contact.setEmail("felixreinaldosiong12@gmail.com");
        contact.setUrl("https://github.com/Fel-1/mini-project");

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info().title("Spring Boot REST API Fasilitas Kesehatan")
                        .contact(contact)
                        .description("Spring Boot REST API Untuk Menampilkan Daftar Fasilitas Kesehatan")
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
