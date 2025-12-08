package com.IngSoftware.proyectosgr.service;

import com.IngSoftware.proyectosgr.domain.model.ChangePasswordToken;
import org.springframework.stereotype.Service;


public interface ChangePasswordTokenService {
    Integer generateNewToken() throws Exception;
    ChangePasswordToken save(ChangePasswordToken request) throws Exception;
}
