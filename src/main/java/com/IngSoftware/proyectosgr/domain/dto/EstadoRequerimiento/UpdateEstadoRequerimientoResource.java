package com.IngSoftware.proyectosgr.domain.dto.EstadoRequerimiento;

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
public class UpdateEstadoRequerimientoResource {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 30, message = "La descripción no puede exceder los 30 caracteres")
    private String descripcion;

    @NotNull(message = "El orden es obligatorio")
    private Integer orden;

    @Size(max = 30, message = "La abreviatura no puede exceder los 30 caracteres")
    private String abr;
}
