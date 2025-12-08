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
@Table(name = "change_password_token")
public class ChangePasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "change_password_token_id")
    private Integer id;

    @Column(name = "token", nullable = false, length = 6)
    private Integer token;

    @OneToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "usuario_id")
    private Usuario users;
}
