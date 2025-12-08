package com.IngSoftware.proyectosgr.domain.dto.User;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResource {
    private Integer id;
    private String firstname;
    private String lastname;
    private String fullname;
    private String password;
    private String email;
    private Boolean active;
    private Date registrationDate;
}
