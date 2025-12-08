package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.domain.model.PrincipalUser;
import com.IngSoftware.proyectosgr.domain.model.Usuario;
import com.IngSoftware.proyectosgr.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    UsuarioService usuarioService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> optionalUser = usuarioService.getUserByEmail(email);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException(String.format("No se encontró ningun usuario con email %s", email));
        }

        return PrincipalUser.build(optionalUser.get());
    }
}
