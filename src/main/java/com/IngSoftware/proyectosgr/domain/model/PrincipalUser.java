package com.IngSoftware.proyectosgr.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PrincipalUser implements UserDetails {

    private Integer idusuario;
    private String username;    // usuario
    private String nombres;     // nombres
    private String apepaterno;
    private String apematerno;
    private String correo;
    private String password;    // clave
    private Collection<? extends GrantedAuthority> authorities;

    public PrincipalUser(Integer idusuario,
                         String username,
                         String nombres,
                         String apepaterno,
                         String apematerno,
                         String correo,
                         String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.idusuario = idusuario;
        this.username = username;
        this.nombres = nombres;
        this.apepaterno = apepaterno;
        this.apematerno = apematerno;
        this.correo = correo;
        this.password = password;
        this.authorities = authorities;
    }

    public static PrincipalUser build(Usuario userAccount) {
        List<GrantedAuthority> authorities =
                userAccount.getRoles().stream()
                        .map(rol -> new SimpleGrantedAuthority(rol.getName().name()))
                        .collect(Collectors.toList());

        return new PrincipalUser(
                userAccount.getIdUsuario(),
                userAccount.getUsuario(),      // → campo nuevo
                userAccount.getNombres(),
                userAccount.getApepaterno(),
                userAccount.getApematerno(),
                userAccount.getCorreo(),       // → correo
                userAccount.getClave(),        // → clave = password
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password; // clave
    }

    @Override
    public String getUsername() {
        return username; // usuario
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; // o userAccount.getEstado() si deseas
    }
}

