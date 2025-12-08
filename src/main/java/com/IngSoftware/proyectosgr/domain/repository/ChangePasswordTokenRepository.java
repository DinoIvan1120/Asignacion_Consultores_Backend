package com.IngSoftware.proyectosgr.domain.repository;

import com.IngSoftware.proyectosgr.domain.model.ChangePasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChangePasswordTokenRepository extends JpaRepository<ChangePasswordToken, Integer> {
    void deleteAllByUsers_IdUsuario(Integer userId);
    @Query("select c from ChangePasswordToken c where c.users.idUsuario = ?1")
    Optional<ChangePasswordToken> findByUserId(Integer userId);

    @Query("select c from ChangePasswordToken c where c.token = ?1")
    Optional<ChangePasswordToken> findByToken(Integer token);
}

