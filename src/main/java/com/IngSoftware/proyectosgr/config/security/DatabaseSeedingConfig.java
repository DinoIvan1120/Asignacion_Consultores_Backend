package com.IngSoftware.proyectosgr.config.security;

import com.IngSoftware.proyectosgr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeedingConfig {
    @Autowired
    RoleService roleService;
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) throws Exception {
        roleService.seed();
    }
}
