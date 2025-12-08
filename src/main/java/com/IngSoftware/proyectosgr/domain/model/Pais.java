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
@Table(name = "pais")
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "iso_num")
    private Integer isoNum;

    @Column(name = "iso_2", length = 2)
    private String iso2;

    @Column(name = "iso_3", length = 3)
    private String iso3;

    @Column(name = "nombre", length = 80)
    private String nombre;

    @Column(name = "ispersonal", length = 1)
    private String ispersonal;
}
