package com.IngSoftware.proyectosgr.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idempresa")
    private Integer id;

    @Column(name = "idmoneda")
    private Integer idmoneda;

    @Column(name = "idfrente")
    private Integer idfrente;

    @Column(name = "razonsocial", length = 80)
    private String razonsocial;

    @Column(name = "nombrecomercial", length = 80)
    private String nombrecomercial;

    @Column(name = "ruc", length = 20)
    private String ruc;

    @Column(name = "direccion", length = 80)
    private String direccion;

    @Column(name = "telefono", length = 20)
    private String telefono;

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

    @Column(name = "codinterno")
    private Boolean codinterno;

    @Column(name = "logo", length = 250)
    private String logo;

    @Column(name = "prefijonombrecomercial", length = 3)
    private String prefijonombrecomercial;

    @Column(name = "usuario_solicitante")
    private Boolean usuarioSolicitante;

    @Column(name = "activar_pais")
    private Boolean activarPais;

    @Column(name = "tiempo_prueba")
    private Integer tiempoPrueba;

    @Column(name = "aplica_detraccion")
    private Boolean aplicaDetraccion;

    @Column(name = "empresa_padre")
    private Integer empresaPadre;

    @Column(name = "prioridad")
    private Boolean prioridad;

    @Column(name = "aplica_cierre_automatico")
    private Boolean aplicaCierreAutomatico;

    @Column(name = "idempresa_sap", length = 10)
    private String idempresaSap;

    @Column(name = "id_bukrs", length = 4)
    private String idBukrs;

    // Relaciones opcionales con otras entidades (si las tienes)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmoneda", insertable = false, updatable = false)
    private Moneda moneda;

    // Relaciones opcionales con otras entidades (si las tienes)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfrente", insertable = false, updatable = false)
    private Frente frente;

    // Relación ManyToMany con Pais
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "maestro_empresa_pais",
            joinColumns = @JoinColumn(
                    name = "id_empresa",
                    foreignKey = @ForeignKey(name = "FK_maestro_empresa_pais_empresa")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "id_pais",
                    foreignKey = @ForeignKey(name = "FK_maestro_empresa_pais_pais")
            )
    )
    private List<Pais> paises = new ArrayList<>();

}
