package com.IngSoftware.proyectosgr.domain.dto.SubFrente;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSubFrenteResource {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 20, message = "La descripción no puede exceder los 20 caracteres")
    private String descripcion;

    @NotNull(message = "El id del frente es obligatorio")
    private Integer idFrente;

    @NotNull(message = "El color es obligatorio")
    private Integer color;
}
