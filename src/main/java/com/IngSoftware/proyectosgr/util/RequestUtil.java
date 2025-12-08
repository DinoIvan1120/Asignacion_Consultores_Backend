package com.IngSoftware.proyectosgr.util;

import com.IngSoftware.proyectosgr.config.security.JwtProvider;
import com.IngSoftware.proyectosgr.domain.enumeration.Rolename;
import com.IngSoftware.proyectosgr.domain.model.Usuario;
import com.IngSoftware.proyectosgr.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestUtil {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtProvider provider;
    @Autowired
    private UsuarioRepository usersRepository;
    public String getUserToken(){
        return request.getHeader("Authorization").substring(7);
    }
    public Integer getUserId(){
        return this.provider.getUserIdFromToken(getUserToken());
    }
    public Boolean isAdmin(){
        Usuario user = this.usersRepository.findById(this.getUserId()).get();
        return user.getRoles().stream().anyMatch(r -> r.getName().name().equals(Rolename.Administrador.name()));
    }
}