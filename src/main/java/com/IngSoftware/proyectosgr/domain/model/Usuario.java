package com.IngSoftware.proyectosgr.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer idUsuario;

    @Column(name = "idtipousuario")
    private Integer idTipoUsuario;

    @Column(name = "apepaterno", length = 80)
    private String apepaterno;

    @Column(name = "apematerno", length = 80)
    private String apematerno;

    @Column(name = "nombres", length = 80)
    private String nombres;

    @Column(name = "correo", length = 80)
    private String correo;

    @Column(name = "usuario", length = 80)
    private String usuario;

    @Column(name = "clave", length = 255)
    private String clave;

    @CreationTimestamp
    @Column(name = "fecharegistro", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharegistro;

    @UpdateTimestamp
    @Column(name = "fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodificacion;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "foto", length = 1250)
    private String foto;

    @Column(name = "telefonomovil", length = 20)
    private String telefonomovil;

    @Column(name = "idempresa")
    private Integer idempresa;

    @Column(name = "url_foto", length = 200)
    private String urlFoto;

    @Column(name = "sexo", length = 1)
    private String sexo;

    @Column(name = "idpais")
    private Integer idpais;

    @Column(name = "idtipodocumento")
    private Integer idtipodocumento;

    @Column(name = "ruc")
    private String ruc;

    @Column(name = "tipodocumento", length = 15)
    private String tipodocumento;

    @Column(name = "idestadocivil")
    private Integer idestadocivil;

    @Column(name = "fechanacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechanacimiento;

    @Column(name = "es_personal", length = 1)
    private String esPersonal;

    @Column(name = "estado_personal", length = 1)
    private String estadoPersonal;

    @Column(name = "direccion", length = 100)
    private String direccion;

    @Column(name = "especialidad", length = 100)
    private String especialidad;

    @Column(name = "codigo", length = 20)
    private String codigo;

    @Column(name = "fijo", length = 20)
    private String fijo;

    @Column(name = "correo_personal", length = 100)
    private String correoPersonal;

    @Column(name = "flag_ponente")
    private Boolean flagPonente;

    @Column(name = "fechacesecuenta")
    @Temporal(TemporalType.DATE)
    private Date fechacesecuenta;

    @Column(name = "olvide_password", length = 200)
    private String olvidePassword;

    @Column(name = "id_area")
    private Integer idArea;

    @Column(name = "soporte_ext")
    private Boolean soporteExt;

    @Column(name = "id_ccosto")
    private Integer idCcosto;

    @Column(name = "acreedor", length = 7)
    private String acreedor;

    @Column(name = "asignacion", length = 25)
    private String asignacion;

    // Relación con roles para autenticación
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();

    // Relaciones opcionales con otras entidades (si las tienes)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtipousuario", insertable = false, updatable = false)
    private TipoUsuario tipoUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idempresa", insertable = false, updatable = false)
    private Empresa empresa;
}
