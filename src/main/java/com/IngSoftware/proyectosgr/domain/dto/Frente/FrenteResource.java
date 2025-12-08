package com.IngSoftware.proyectosgr.domain.dto.Frente;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FrenteResource {

    private Integer idfrente;
    private String descripcion;
    private String correos;
    private Integer idArea;
}
