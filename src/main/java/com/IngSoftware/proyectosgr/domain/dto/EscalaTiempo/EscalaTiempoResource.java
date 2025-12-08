package com.IngSoftware.proyectosgr.domain.dto.EscalaTiempo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EscalaTiempoResource {

    private Integer id;
    private String descripcion;
    private Integer tiempoEnHooras;
}
