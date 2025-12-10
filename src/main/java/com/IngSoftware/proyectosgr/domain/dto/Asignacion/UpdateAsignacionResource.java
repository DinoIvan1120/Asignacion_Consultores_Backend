package com.IngSoftware.proyectosgr.domain.dto.Asignacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAsignacionResource {

    /**
     * Datos del requerimiento a actualizar
     */
    @Valid
    private RequerimientoUpdateDTO requerimiento;

    /**
     * Lista de actividades a actualizar
     */
    @Valid
    private List<ActividadUpdateDTO> actividades;

    // ============================================================
    // DTO interno para actualizar requerimiento
    // ============================================================
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequerimientoUpdateDTO {

        @Size(max = 121, message = "El título no puede exceder 121 caracteres")
        private String titulo;

        private String detalle;

        private Integer idSubfrente;

        private Integer idUsuario;

        private Integer idEmpresa;

        private String descripcionEstimacion;

        private String detalleAsignacion;

        @Size(max = 50, message = "La orden de compra no puede exceder 50 caracteres")
        private String ordenCompra;
    }

    // ============================================================
    // DTO interno para actualizar actividad
    // ============================================================
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ActividadUpdateDTO {

        /**
         * ID de la actividad a actualizar (obligatorio)
         */
        @NotNull(message = "El ID de la actividad es obligatorio")
        private Integer id;

        private Integer idusuario;

        private Date fechainicio;

        private Integer idmoneda;

        private Date fechafin;

        private Integer idtipoactividad;

        @Positive(message = "El tiempo regular debe ser mayor a 0")
        private Double tiemporegular;

        @Positive(message = "El costo debe ser mayor a 0")
        private Double costo;

        private Boolean facturable;

        private Double tiempoextra;

        private Double porcentajeAvance;

        @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
        private String descripcion;
    }
}
