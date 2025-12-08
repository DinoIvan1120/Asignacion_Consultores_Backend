package com.IngSoftware.proyectosgr.domain.dto.Prioridad;

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
public class UpdatePrioridadResource {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 20, message = "La descripción no puede exceder los 20 caracteres")
    private String descripcion;

    @Size(max = 100, message = "La observación no puede exceder los 100 caracteres")
    private String observacion;

    @NotNull(message = "El orden es obligatorio")
    private Integer orden;
}
