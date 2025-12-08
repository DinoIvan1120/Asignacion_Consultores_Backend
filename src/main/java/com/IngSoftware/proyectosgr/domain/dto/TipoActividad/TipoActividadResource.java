package com.IngSoftware.proyectosgr.domain.dto.TipoActividad;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoActividadResource {

    private Integer id;
    private String descripcion;
    private String tipotrabajo;
    private String mostrar;
    private String tipoTrabajoReporte;
}
