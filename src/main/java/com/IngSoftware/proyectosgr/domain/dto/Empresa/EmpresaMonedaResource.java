package com.IngSoftware.proyectosgr.domain.dto.Empresa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaMonedaResource {

    private Integer idempresa;
    private String nombreComercial;
    private Integer idmoneda;
    private String descripcion;
}
