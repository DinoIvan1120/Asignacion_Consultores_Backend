package com.IngSoftware.proyectosgr.domain.dto.SubFrente;

import com.IngSoftware.proyectosgr.domain.dto.Frente.FrenteResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubFrenteResource {

    private Integer idSubfrente;
    private String descripcion;
    private Integer idFrente;
    private Integer color;

    //Relaciones
    private FrenteResource frente;
}
