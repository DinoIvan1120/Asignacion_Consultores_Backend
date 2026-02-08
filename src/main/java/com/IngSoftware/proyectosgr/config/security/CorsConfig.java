package com.IngSoftware.proyectosgr.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://10.0.2.16:5554")
                .allowedOrigins("http://localhost:5173")
                .allowedOrigins("http://localhost:5174")
                .allowedOrigins("https://proyectosgr.vercel.app")
                .allowedOrigins("https://sgr-backend.duckdns.org")
                .allowedOrigins("https://savelife-upc-f0dee3255ae0.herokuapp.com")
                .allowedOrigins("http://127.0.0.1:5000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://10.0.2.16:5554");
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://localhost:5174");
        config.addAllowedOrigin("https://proyectosgr.vercel.app");
        config.addAllowedOrigin("https://sgr-backend.duckdns.org");
        config.addAllowedOrigin("https://savelife-upc-f0dee3255ae0.herokuapp.com");
        config.addAllowedOrigin("http://127.0.0.1:5000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}