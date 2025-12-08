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
@Table(name = "escalatiempo")
public class EscalaTiempo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idescalatiempo")
    private Integer id;

    @Column(columnDefinition = "text")
    private String descripcion;

    @Column(name = "tiempoenhooras", columnDefinition = "int4 default 0", nullable = false)
    private Integer tiempoEnHooras = 0;
}
