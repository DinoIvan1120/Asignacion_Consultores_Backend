package com.IngSoftware.proyectosgr.service;

import com.IngSoftware.proyectosgr.domain.enumeration.Rolename;
import com.IngSoftware.proyectosgr.domain.model.Roles;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface RoleService {
    List<Roles> getAllRoles();
    Optional<Roles> findByName(Rolename rolname);
    void seed();

}
