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
@Table(name = "prioridad")
public class Prioridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprioridad")
    private Integer idPrioridad;

    @Column(name = "descripcion", length = 20)
    private String descripcion;

    @Column(name = "observacion", length = 100)
    private String observacion;

    @Column(name = "orden")
    private Integer orden;

}
