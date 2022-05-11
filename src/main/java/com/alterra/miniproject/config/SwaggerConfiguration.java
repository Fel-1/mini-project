package com.alterra.miniproject.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
//@EnableSwagger2
//@SecurityScheme(
//        name = "bearerAuth",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        scheme = "bearer"
//)
public class SwaggerConfiguration {
//    @Bean
//    public Docket productApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.alterra.miniproject.controller"))
//                .paths(regex("/.*"))
//                .build()
//                .apiInfo(metaData());
//    }
//    private ApiInfo metaData() {
//        ApiInfo apiInfo = new ApiInfo(
//                "Spring Boot REST API Fasilitas Kesehatan",
//                "Spring Boot REST API Untuk Menampilkan Daftar Fasilitas Kesehatan",
//                "1.0",
//                "Terms of service",
//                new Contact(
//                        "Felix Reinaldo",
//                        "https://github.com/Fel-1/mini-project",
//                        "felixreinaldosiong12@gmail.com"
//                ),
//                "Apache License Version 2.0",
//                "https://www.apache.org/licenses/LICENSE-2.0");
//        return apiInfo;
//    }

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
