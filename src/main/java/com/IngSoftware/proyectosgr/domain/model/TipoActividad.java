package com.IngSoftware.proyectosgr.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tipoactividad")
public class TipoActividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipoactividad")
    private Integer id;

    @Column(length = 100)
    private String descripcion;

    @Column(length = 100)
    private String tipotrabajo;

    @Column(columnDefinition = "bpchar(1)")
    private String mostrar;

    @Column(name = "tipotrabajoreporte", length = 100)
    private String tipoTrabajoReporte;
}
