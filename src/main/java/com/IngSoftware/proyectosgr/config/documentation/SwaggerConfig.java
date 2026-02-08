package com.IngSoftware.proyectosgr.config.documentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${IngSoftware.proyectosgr.prd-url}")
    private String apiUrl;

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .host(apiUrl)
                .protocols(new HashSet<>(Arrays.asList("https", "http")))
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.IngSoftware.proyectosgr.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    // Información que aparecerá en la UI de Swagger
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API Proyecto Sistema SGR") // título grande
                .description("Documentación de los servicios REST del sistema de Gestión de Proyectos SGR.") // descripción
                .version("1.0.0") // versión del API
                .contact(new Contact(
                        "Equipo de Desarrollo - IngSoftware",
                        "https://www.ingsoftware.com",
                        "contacto@ingsoftware.com"
                ))
                .license("Licencia MIT")
                .licenseUrl("https://opensource.org/licenses/MIT")
                .build();
    }
    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }
    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
}