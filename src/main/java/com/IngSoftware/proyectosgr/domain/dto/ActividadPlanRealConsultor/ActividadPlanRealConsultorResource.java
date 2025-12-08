package com.IngSoftware.proyectosgr.domain.dto.ActividadPlanRealConsultor;

import com.IngSoftware.proyectosgr.domain.dto.CategoriaActividad.CategoriaActividadResource;
import com.IngSoftware.proyectosgr.domain.dto.Citizen.CitizenResource;
import com.IngSoftware.proyectosgr.domain.dto.EscalaTiempo.EscalaTiempoResource;
import com.IngSoftware.proyectosgr.domain.dto.Moneda.MonedaResource;
import com.IngSoftware.proyectosgr.domain.dto.Requerimiento.RequerimientoResource;
import com.IngSoftware.proyectosgr.domain.dto.TipoActividad.TipoActividadResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActividadPlanRealConsultorResource {

    private Integer id;
    private Integer idmoneda;
    private Integer idescalatiempo;
    private Integer idrequerimiento;
    private Integer idtipoactividad;
    private String descripcion;
    private Double tiemporegular;
    private Double tiempoextra;
    private Date fechainicio;
    private Date fechafin;
    private Date fecharegistro;
    private String modo;
    private Double costo;
    private Boolean facturable;
    private Integer idasignacion;
    private Double horasacumuladas;
    private Boolean responsable;
    private Integer idusuario;
    private Double porcentajeAvance;
    private Boolean esMovil;
    private String preAsignado;
    private Integer idCategoria;
    private Date fechaMinRegistro;
    private String cliente;
    private String regtitulo;

    // ===================================================
    // ✅ OBJETOS RELACIONADOS COMPLETOS (DTOs ANIDADOS)
    // ===================================================

    /**
     * Moneda asociada a la actividad
     * Ejemplo: { "idMoneda": 1, "descripcion": "Soles" }
     */
    private MonedaResource moneda;

    /**
     * Tipo de actividad
     * Ejemplo: { "idTipoActividad": 5, "descripcion": "Desarrollo" }
     */
    private TipoActividadResource tipoActividad;

    /**
     * Escala de tiempo
     * Ejemplo: { "idEscalaTiempo": 1, "descripcion": "Horas" }
     */
    private EscalaTiempoResource escalaTiempo;

    /**
     * Requerimiento asociado
     * Ejemplo: { "idRequerimiento": 50600, "codRequerimiento": "3SE-2025-0002" }
     */
    private RequerimientoResource requerimiento;

    /**
     * Categoría de actividad
     * Ejemplo: { "idCategoria": 1, "descripcion": "Backend" }
     */
    private CategoriaActividadResource categoriaActividad;

    /**
     * Usuario/Consultor asignado
     * Ejemplo: { "idusuario": 477, "nombres": "KIYOKO", "apepaterno": "ALIAGA" }
     */
    private CitizenResource usuario;
}
