package com.IngSoftware.proyectosgr.domain.dto.User;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserInformationResource {

    private Integer idUsuario;
    private String usuario;
    private String correo;
    private String nombres;
    private String apepaterno;
    private String apematerno;
    private String nombreCompleto;
    private String rol;
    private Boolean estado;
    private String telefonomovil;
    private Integer idempresa;
    private String nombreEmpresa;
    private Date fecharegistro;
}
