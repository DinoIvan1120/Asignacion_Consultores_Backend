package com.IngSoftware.proyectosgr.domain.dto.ActividadPlanRealConsultor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateActividadPlanRealConsultorResource {
    // ============================================================
    // ✅ CAMPOS OBLIGATORIOS que el FRONTEND debe enviar
    // ============================================================

    @NotNull(message = "El usuario/consultor es obligatorio")
    private Integer idusuario;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private Date fechainicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private Date fechafin;

    @NotNull(message = "El tipo de actividad es obligatorio")
    private Integer idtipoactividad;

    @NotNull(message = "El tiempo regular es obligatorio")
    @Positive(message = "El tiempo regular debe ser mayor a 0")
    private Double tiemporegular;

    @NotNull(message = "El costo es obligatorio")
    @Positive(message = "El costo debe ser mayor a 0")
    private Double costo;

    // ============================================================
    // ✅ CAMPOS OPCIONALES que el FRONTEND puede enviar
    // ============================================================

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    private Boolean facturable;  // Si no se envía, será true por defecto

}
