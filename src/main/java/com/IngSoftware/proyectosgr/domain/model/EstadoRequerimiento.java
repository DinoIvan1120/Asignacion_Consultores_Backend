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
@Table(name = "estadorequerimiento")
public class EstadoRequerimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestadorequerimiento")
    private Integer idEstadoRequerimiento;

    @Column(name = "descripcion", length = 30)
    private String descripcion;

    @Column(name = "orden")
    private Integer orden;

    @Column(name = "abr", length = 30)
    private String abr;
}
