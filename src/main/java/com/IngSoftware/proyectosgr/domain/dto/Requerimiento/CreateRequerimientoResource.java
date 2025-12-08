package com.IngSoftware.proyectosgr.domain.dto.Requerimiento;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateRequerimientoResource {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 121, message = "El título no puede exceder 121 caracteres")
    private String titulo;

    @NotNull(message = "La empresa es obligatoria")
    private Integer idEmpresa;

    @NotNull(message = "El subfrente es obligatorio")
    private Integer idSubfrente;

    @NotNull(message = "El usuario/consultor es obligatorio")
    private Integer idUsuario;

    // ============================================================
    // ✅ CAMPOS OPCIONALES que el FRONTEND puede enviar
    // ============================================================

    private String detalle;  // Descripción detallada del requerimiento (puede estar vacío)

    private String descripcionEstimacion;  // Ejemplo: "6000.0 Soles."

    private String detalleAsignacion;  // Ejemplo: "Proyecto", "Soporte", "Mantenimiento"

    @Size(max = 50, message = "La orden de compra no puede exceder 50 caracteres")
    private String ordenCompra;  // Si tiene orden de compra asociada
}
