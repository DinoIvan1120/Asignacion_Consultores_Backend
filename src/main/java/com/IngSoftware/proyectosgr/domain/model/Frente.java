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
@Table(name = "frente")
public class Frente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfrente")
    private Integer idfrente;

    @Column(length = 50)
    private String descripcion;

    @Column(length = 500)
    private String correos;

    @Column(name = "id_area")
    private Integer idArea;
}
