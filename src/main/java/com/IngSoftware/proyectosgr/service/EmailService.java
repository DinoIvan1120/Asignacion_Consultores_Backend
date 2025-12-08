package com.IngSoftware.proyectosgr.service;

import org.springframework.stereotype.Service;

public interface EmailService {
    void sendEmail(Integer userId) throws Exception;
    String getEmailTemplate(Integer userId) throws Exception;
}