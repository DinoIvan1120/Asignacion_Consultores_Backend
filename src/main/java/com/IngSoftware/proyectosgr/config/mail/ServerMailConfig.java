package com.IngSoftware.proyectosgr.config.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class ServerMailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("notificacioncstiproyectosgr@gmail.com");
        mailSender.setPassword("zizs lrby zjvv gzvh");
        //mailSender.setUsername("dinoivan11201994@gmail.com");
        //mailSender.setPassword("hukcsbqbgkextqzy");
        //mailSender.setPassword("hukc sbqb gkex tqzy");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}