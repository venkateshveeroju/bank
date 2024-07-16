package com.bank.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SwaggerConfig {
    String schemeName = "bearerAuth";
    String bearerFormat = "JWT";
    String scheme = "bearer";

    @Bean
    @Primary
    public OpenAPI caseOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(schemeName)).components(new Components()
                        .addSecuritySchemes(
                                schemeName, new SecurityScheme()
                                        .name(schemeName)
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .bearerFormat(bearerFormat)
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme(scheme)
                        )
                )
                .info(new Info()
                        .title("Banking API Specification for account operations")
                        .description("A simple banking API that allows two operations:\n" +
                                "    - API to open an account with the option to add initial credit.\n" +
                                "    - API to retrieve account information including account balance.\n" +
                                "    - API to retrieve person information.\n" +
                                "    - API to make a transfer from one account to another.")
                        .version("1.0")
                );
    }
    @Bean
    public OpenAPI caseOpenAPIV2() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(schemeName)).components(new Components()
                        .addSecuritySchemes(
                                schemeName, new SecurityScheme()
                                        .name(schemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .bearerFormat(bearerFormat)
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme(scheme)
                        )
                )
                .info(new Info()
                        .title("Banking API Specification for account operations")
                        .description("A simple banking API that allows two operations:\n" +
                                "    - API to open an account with the option to add initial credit.\n" +
                                "    - API to retrieve account information including account balance.\n" +
                                "    - API to retrieve person information.\n" +
                                "    - API to make a transfer from one account to another.")
                        .version("2.0")
                );
    }
}