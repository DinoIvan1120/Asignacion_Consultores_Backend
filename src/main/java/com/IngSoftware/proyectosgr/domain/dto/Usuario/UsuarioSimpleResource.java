package com.IngSoftware.proyectosgr.domain.dto.Usuario;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSimpleResource {

    private Integer idUsuario;
    private String nombres;
    private String apepaterno;
    private String apematerno;
    private String nombreCompleto;
    private String correo;
    private Integer idTipoUsuario;
    private String descripcionTipoUsuario;
    private Boolean estado;
}
