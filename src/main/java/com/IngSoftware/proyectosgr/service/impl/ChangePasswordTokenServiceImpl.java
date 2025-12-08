package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.domain.model.ChangePasswordToken;
import com.IngSoftware.proyectosgr.domain.repository.ChangePasswordTokenRepository;
import com.IngSoftware.proyectosgr.domain.repository.UsuarioRepository;
import com.IngSoftware.proyectosgr.service.ChangePasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ChangePasswordTokenServiceImpl implements ChangePasswordTokenService {
    @Autowired
    private ChangePasswordTokenRepository changePasswordTokenRepository;
    @Autowired
    private UsuarioRepository usersRepository;
    @Override
    public Integer generateNewToken() throws Exception {
        Integer min = 100001;
        Integer max = 999999;
        Integer token = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return token;
    }

    @Override
    public ChangePasswordToken save(ChangePasswordToken request) throws Exception {
        return null;
    }


}