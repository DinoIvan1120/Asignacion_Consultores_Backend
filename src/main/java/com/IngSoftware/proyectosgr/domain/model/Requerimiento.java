package com.IngSoftware.proyectosgr.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requerimiento")
public class Requerimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrequerimiento")
    private Integer idRequerimiento;

    @Column(name = "idestadorequerimiento")
    private Integer idEstadoRequerimiento;

    @Column(name = "idprioridad")
    private Integer idPrioridad;

    @Column(name = "idusuarioxempxsubfrente")
    private Integer idUsuarioXEmpXSubfrente;

    @Column(name = "activarextra")
    private Boolean activarExtra;

    @Column(name = "codrequerimiento", length = 50)
    private String codRequerimiento;

    @Column(name = "codinternoreq", length = 50)
    private String codInternoReq;

    @Column(name = "titulo", length = 121)
    private String titulo;

    @Column(name = "detalle", columnDefinition = "TEXT")
    private String detalle;

    @CreationTimestamp
    @Column(name = "fecharegistro", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @Column(name = "urgente")
    private Boolean urgente;

    @Column(name = "tipofacturacion", length = 1)
    private String tipoFacturacion;

    @Column(name = "idcoordinador")
    private Integer idCoordinador;

    @Column(name = "fechaenvio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;

    @Column(name = "fecharequerimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRequerimiento;

    @Column(name = "idempresa")
    private Integer idEmpresa;

    @Column(name = "idsubfrente")
    private Integer idSubfrente;

    @Column(name = "idusuario")
    private Integer idUsuario;

    @Column(name = "costorecalculado", length = 1)
    private String costoRecalculado;

    @Column(name = "costorequerimiento")
    private Double costoRequerimiento;

    @Column(name = "fechacancelacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCancelacion;

    @Column(name = "fechacierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;

    @Column(name = "descripcionestimacion", columnDefinition = "TEXT")
    private String descripcionEstimacion;

    @Column(name = "id_pais")
    private Integer idPais;

    @Column(name = "avance_automatico")
    private Boolean avanceAutomatico;

    @Column(name = "adjunto_nombre_estimacion", length = 100)
    private String adjuntoNombreEstimacion;

    @Column(name = "adjunto_url_estimacion", length = 200)
    private String adjuntoUrlEstimacion;

    @Column(name = "notificando_cierre")
    private Boolean notificandoCierre;

    @Column(name = "semaforo", length = 2)
    private String semaforo;

    @Column(name = "aplica_bono_coordinador", length = 1)
    private String aplicaBonoCoordinador;

    @Column(name = "orden_compra", length = 50)
    private String ordenCompra;

    @Column(name = "detalle_asignacion", columnDefinition = "TEXT")
    private String detalleAsignacion;

    @Column(name = "aplica_cierre_automatico")
    private Boolean aplicaCierreAutomatico;

    @Column(name = "sw_factory")
    private Boolean swFactory;

    @Column(name = "id_consultor")
    private Integer idConsultor;

    @Column(name = "id_comercial")
    private Integer idComercial;

    @Column(name = "id_oportunidad")
    private Integer idOportunidad;

    @Column(name = "id_tipo_requerimiento")
    private Integer idTipoRequerimiento;

    @Column(name = "id_tipo_servicio")
    private Integer idTipoServicio;

    @Column(name = "id_area")
    private Integer idArea;

    @Column(name = "id_unidad_organizativa")
    private Integer idUnidadOrganizativa;

    @Column(name = "id_estado_incidencia")
    private Integer idEstadoIncidencia;

    @Column(name = "id_proceso_incidencia")
    private Integer idProcesoIncidencia;

    @Column(name = "id_tipo_incidencia")
    private Integer idTipoIncidencia;

    // Relaciones con otras entidades
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idestadorequerimiento", insertable = false, updatable = false)
    private EstadoRequerimiento estadoRequerimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idprioridad", insertable = false, updatable = false)
    private Prioridad prioridad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idempresa", insertable = false, updatable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idsubfrente", insertable = false, updatable = false)
    private SubFrente subfrente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais", insertable = false, updatable = false)
    private Pais pais;

}
