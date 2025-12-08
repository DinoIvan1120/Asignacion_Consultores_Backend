package com.IngSoftware.proyectosgr.config.security;

import com.IngSoftware.proyectosgr.domain.model.PrincipalUser;
import com.IngSoftware.proyectosgr.domain.repository.UsuarioRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    //private int expirationDays;
    private long expirationDays;
    @Autowired
    private UsuarioRepository usersRepository;

    public String generateToken(Authentication authentication) {
        PrincipalUser principalUser =(PrincipalUser) authentication.getPrincipal();
        List<String> roles=principalUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        Integer userId = usersRepository.getUserIdByEmail(principalUser.getUsername());
        return Jwts.builder()
                .setSubject(principalUser.getUsername())
                .claim("user_id",userId)
                .claim("roles",roles)
                .setIssuedAt(new Date())
                //.setExpiration(new Date(new Date().getTime() + expirationDays * 180000))
                .setExpiration(new Date(System.currentTimeMillis() + expirationDays))
                .signWith(SignatureAlgorithm.HS512,secret.getBytes())
                .compact();
    }
    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }
    public Integer getUserIdFromToken(String token){
        return this.usersRepository.getUserIdByEmail(this.getUsernameFromToken(token));
    }
    public List<String> getUserRoles(String token){
        Jwt jwt = Jwts.parser().parse(token);
        Jwts.parser().parseClaimsJws(token);
        List<String> r = new ArrayList<>();
        return r;
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException(e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException(e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new RuntimeException(String.format("El token expiró: %s",e.getMessage()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (SignatureException e) {
            throw new SignatureException(e.getMessage());
        }
    }

}
