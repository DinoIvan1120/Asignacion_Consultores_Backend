package com.IngSoftware.proyectosgr.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "citizen")
@PrimaryKeyJoinColumn(referencedColumnName = "idusuario")
public class Citizen extends Usuario {
    @Column(name = "alias")
    private String alias;

}
