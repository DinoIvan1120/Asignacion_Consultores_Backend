package com.IngSoftware.proyectosgr.domain.dto.Empresa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaPaisResource {

    private Integer idEmpresa;
    private String nombreComercial;
    private Integer idPais;
    private String nombre;

}
