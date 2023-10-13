package com.salesianostriana.dam.gradesapi.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("GRADESAPP API")
                        .description("API REST para gestionar las notas del alumnado")
                        .version("v0.0.1")
                        .license(new License().name("Creative Commons Zero v1.0 Universal").url("https://github.com/SebastianMillan/Project-GRADESAPI"))
                ).externalDocs(new ExternalDocumentation()
                        .description("")
                        .url("")

                );
    }
}
