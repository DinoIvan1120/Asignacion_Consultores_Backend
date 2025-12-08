package com.IngSoftware.proyectosgr.domain.dto.EstadoRequerimiento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstadoRequerimientoResource {

    private Integer idEstadoRequerimiento;
    private String descripcion;
    private Integer orden;
    private String abr;
}
