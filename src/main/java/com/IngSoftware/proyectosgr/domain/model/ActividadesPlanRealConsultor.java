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
@Table(name = "actividadesplanrealconsultor")
public class ActividadesPlanRealConsultor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idactividadrealplan")
    private Integer id;

    @Column(name = "idmoneda", nullable = false)
    private Integer idmoneda;

    @Column(name = "idescalatiempo", nullable = false)
    private Integer idescalatiempo;

    @Column(name = "idrequerimiento", nullable = false)
    private Integer idrequerimiento;

    @Column(name = "idtipoactividad", nullable = false)
    private Integer idtipoactividad;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tiemporegular")
    private Double tiemporegular;

    @Column(name = "tiempoextra")
    private Double tiempoextra;

    @Column(name = "fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;

    @Column(name = "fechafin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafin;

    @CreationTimestamp
    @Column(name = "fecharegistro", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharegistro;

    @Column(name = "modo", length = 1)
    private String modo;

    @Column(name = "costo")
    private Double costo;

    @Column(name = "facturable")
    private Boolean facturable;

    @Column(name = "idasignacion")
    private Integer idasignacion;

    @Column(name = "horasacumuladas")
    private Double horasacumuladas;

    @Column(name = "responsable")
    private Boolean responsable;

    @Column(name = "idusuario", nullable = false)
    private Integer idusuario;

    @Column(name = "porcentaje_de_avance_aprc")
    private Double porcentajeAvance;

    @Column(name = "es_movil")
    private Boolean esMovil;

    @Column(name = "preasignado", length = 1)
    private String preAsignado;

    @Column(name = "id_categoria")
    private Integer idCategoria;

    @Column(name = "fecha_min_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMinRegistro;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "regtitulo")
    private String regtitulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmoneda", insertable = false, updatable = false)
    private Moneda moneda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtipoactividad", insertable = false, updatable = false)
    private TipoActividad tipoActividad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idescalatiempo", insertable = false, updatable = false)
    private EscalaTiempo escalaTiempo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idrequerimiento", insertable = false, updatable = false)
    private Requerimiento requerimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    private CategoriaActividad categoriaActividad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario", insertable = false, updatable = false)
    private Usuario usuario;


}
