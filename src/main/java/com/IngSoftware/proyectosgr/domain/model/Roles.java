package com.IngSoftware.proyectosgr.domain.model;


import com.IngSoftware.proyectosgr.domain.enumeration.Rolename;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Table(name = "role")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique = true, nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private Rolename name;

}
