package com.IngSoftware.proyectosgr.domain.dto.Jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordResource {
    private String newPassword;
    private Integer token;
    //private String email;
}
