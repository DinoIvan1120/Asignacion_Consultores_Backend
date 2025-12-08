package com.IngSoftware.proyectosgr.domain.dto.Jwt;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResource {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
