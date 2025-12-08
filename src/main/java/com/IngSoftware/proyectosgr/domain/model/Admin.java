package com.IngSoftware.proyectosgr.domain.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin")
@PrimaryKeyJoinColumn(referencedColumnName = "idusuario")
public class Admin extends Usuario {
    @Column(name = "alias")
    private String alias;

}
