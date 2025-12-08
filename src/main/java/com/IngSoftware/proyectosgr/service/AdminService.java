package com.IngSoftware.proyectosgr.service;

import com.IngSoftware.proyectosgr.domain.model.Admin;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    Admin save(Admin admin) throws Exception;
}
