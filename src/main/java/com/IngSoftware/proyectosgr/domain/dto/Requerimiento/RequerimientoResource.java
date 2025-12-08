package com.IngSoftware.proyectosgr.domain.dto.Requerimiento;

import com.IngSoftware.proyectosgr.domain.dto.Citizen.CitizenResource;
import com.IngSoftware.proyectosgr.domain.dto.Empresa.EmpresaResource;
import com.IngSoftware.proyectosgr.domain.dto.EstadoRequerimiento.EstadoRequerimientoResource;
import com.IngSoftware.proyectosgr.domain.dto.Prioridad.PrioridadResource;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class RequerimientoResource {

    private Integer idRequerimiento;
    private Integer idEstadoRequerimiento;
    private Integer idPrioridad;
    private Integer idUsuarioXEmpXSubfrente;
    private Boolean activarExtra;
    private String codRequerimiento;
    private String codInternoReq;
    private String titulo;
    private String detalle;
    private Date fechaRegistro;
    private Boolean urgente;
    private String tipoFacturacion;
    private Integer idCoordinador;
    private Date fechaEnvio;
    private Date fechaRequerimiento;
    private Integer idEmpresa;
    private Integer idSubfrente;
    private Integer idUsuario;
    private String costoRecalculado;
    private Double costoRequerimiento;
    private Date fechaCancelacion;
    private Date fechaCierre;
    private String descripcionEstimacion;
    private Integer idPais;
    private Boolean avanceAutomatico;
    private String adjuntoNombreEstimacion;
    private String adjuntoUrlEstimacion;
    private Boolean notificandoCierre;
    private String semaforo;
    private String aplicaBonoCoordinador;
    private String ordenCompra;
    private String detalleAsignacion;
    private Boolean aplicaCierreAutomatico;
    private Boolean swFactory;
    private Integer idConsultor;
    private Integer idComercial;
    private Integer idOportunidad;
    private Integer idTipoRequerimiento;
    private Integer idTipoServicio;
    private Integer idArea;
    private Integer idUnidadOrganizativa;
    private Integer idEstadoIncidencia;
    private Integer idProcesoIncidencia;
    private Integer idTipoIncidencia;

    // ===================================================
    // ✅ OBJETOS RELACIONADOS COMPLETOS (DTOs ANIDADOS)
    // ===================================================

    /**
     * Estado del requerimiento con su descripción
     * Ejemplo: { "idEstadoRequerimiento": 7, "descripcion": "En Ejecución" }
     */
    private EstadoRequerimientoResource estadoRequerimiento;

    /**
     * Prioridad del requerimiento con su descripción
     * Ejemplo: { "idPrioridad": 2, "descripcion": "Media" }
     */
    private PrioridadResource prioridad;

    /**
     * Empresa asociada al requerimiento
     * Ejemplo: { "idEmpresa": 3, "nombrecomercial": "CSTI CORP S.A.C.", "ruc": "20123456789" }
     */
    private EmpresaResource empresa;

    /**
     * Usuario/Consultor asignado al requerimiento
     * Ejemplo: { "idusuario": 272, "nombres": "ANDRE", "apepaterno": "ALZAMORA" }
     */
    private CitizenResource usuario;
}
