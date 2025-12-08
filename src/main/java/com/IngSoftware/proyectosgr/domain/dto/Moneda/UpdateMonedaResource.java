package com.IngSoftware.proyectosgr.domain.dto.Moneda;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMonedaResource {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 20, message = "La descripción no puede exceder los 20 caracteres")
    private String descripcion;

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 10, message = "El código no puede exceder los 10 caracteres")
    private String codigo;
}
