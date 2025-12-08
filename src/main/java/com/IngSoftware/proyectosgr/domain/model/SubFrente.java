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
@Table(name = "subfrente")
public class SubFrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsubfrente")
    private Integer idSubfrente;

    @Column(name = "descripcion", length = 20)
    private String descripcion;

    @Column(name = "idfrente")
    private Integer idFrente;

    @Column(name = "color")
    private Integer color;

    // Relación con Frente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfrente", insertable = false, updatable = false)
    private Frente frente;
}
