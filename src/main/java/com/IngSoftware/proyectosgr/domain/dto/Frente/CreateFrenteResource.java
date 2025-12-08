package com.IngSoftware.proyectosgr.domain.dto.Frente;


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
public class CreateFrenteResource {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 50, message = "La descripción no puede exceder los 50 caracteres")
    private String descripcion;

    @Size(max = 500, message = "Los correos no pueden exceder los 500 caracteres")
    private String correos;

    @NotNull(message = "El id del área es obligatorio")
    private Integer idArea;
}
