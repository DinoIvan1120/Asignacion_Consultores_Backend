package com.IngSoftware.proyectosgr.service;

import com.IngSoftware.proyectosgr.domain.dto.Citizen.CreateCitizenResource;
import com.IngSoftware.proyectosgr.domain.dto.Jwt.ChangePasswordResource;
import com.IngSoftware.proyectosgr.domain.dto.Jwt.LoginResource;
import com.IngSoftware.proyectosgr.domain.dto.Jwt.TokenResource;
import com.IngSoftware.proyectosgr.domain.enumeration.Rolename;
import com.IngSoftware.proyectosgr.domain.model.Admin;
import com.IngSoftware.proyectosgr.domain.model.Citizen;
import com.IngSoftware.proyectosgr.domain.model.Roles;
import org.springframework.stereotype.Service;

public interface AuthService {
    Roles getCitizenRole() throws Exception;
    Roles getCitizenAllowedRole(Rolename role) throws Exception;
    Roles getAdminRole() throws Exception;
    Citizen registerCitizen(Citizen request, CreateCitizenResource resource) throws Exception;
    Admin registerAdmin(Admin request) throws Exception;
    TokenResource login(LoginResource loginResource) throws Exception;
    String requestPasswordChange(String email) throws Exception;
    String updatePassword(ChangePasswordResource request) throws Exception;
}