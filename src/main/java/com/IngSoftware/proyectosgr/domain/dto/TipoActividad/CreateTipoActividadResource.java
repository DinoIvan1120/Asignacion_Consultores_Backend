package com.IngSoftware.proyectosgr.domain.dto.TipoActividad;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTipoActividadResource {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción no puede exceder los 100 caracteres")
    private String descripcion;

    @NotBlank(message = "El tipo de trabajo es obligatorio")
    @Size(max = 100, message = "El tipo de trabajo no puede exceder los 100 caracteres")
    private String tipotrabajo;

    @NotBlank(message = "El campo mostrar es obligatorio")
    @Pattern(regexp = "^[A-Za-z0-9]$", message = "El campo mostrar debe contener solo 1 caracter")
    private String mostrar;

    @Size(max = 100, message = "El tipo de trabajo para reporte no puede exceder los 100 caracteres")
    private String tipoTrabajoReporte;
}
