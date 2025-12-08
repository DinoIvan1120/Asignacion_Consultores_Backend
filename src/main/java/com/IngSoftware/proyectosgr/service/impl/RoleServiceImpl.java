package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.domain.enumeration.Rolename;
import com.IngSoftware.proyectosgr.domain.model.Roles;
import com.IngSoftware.proyectosgr.domain.repository.RolesRepository;
import com.IngSoftware.proyectosgr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RolesRepository roleRepository;
    // Aquí colocamos EXACTAMENTE los nombres definidos en tu enum Rolename
    private static String[] DEFAULT_ROLES = {
            "Consultor",
            "Cliente",
            "Coordinador",
            "Administrador",
            "Gerente",
            "Facturación",
            "Recursos_Humanos",
            "Lider_Abap"
    };
    @Override
    public List<Roles> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Roles> findByName(Rolename rolname) {
        return this.roleRepository.findByName(rolname);
    }

    @Override
    public void seed() {
        Arrays.stream(DEFAULT_ROLES).forEach(name -> {
            Rolename roleName = Rolename.valueOf(name);
            if(!roleRepository.existsByName(roleName)) {
                roleRepository.save((new Roles()).withName(roleName));
            }
        } );
    }
}