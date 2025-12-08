package com.IngSoftware.proyectosgr.domain.dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserResource {
    private String firstname;
    private String lastname;
    private String password;
    private String email;
}
