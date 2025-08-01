package com.kirahdev.forumhub.infra.security.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // 1. Adiciona o botão "Authorize" e configura o esquema de segurança JWT
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"))
                // 2. Adiciona informações gerais sobre a API
                .info(new Info()
                        .title("FórumHub API")
                        .description("API Rest da aplicação FórumHub, contendo as funcionalidades de CRUD de tópicos, usuários e cursos.")
                        .license(new License()
                                .name("MIT")
                                .url("https://github.com/kirah-dev/forumhub/LICENSE.txt")));
    }
}