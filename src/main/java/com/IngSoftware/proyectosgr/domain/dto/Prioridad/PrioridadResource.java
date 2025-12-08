package com.IngSoftware.proyectosgr.domain.dto.Prioridad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrioridadResource {

    private Integer idPrioridad;
    private String descripcion;
    private String observacion;
    private Integer orden;
}
