package com.IngSoftware.proyectosgr.domain.repository;


import com.IngSoftware.proyectosgr.domain.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Integer> {
    Boolean existsByAlias(String alias);
    Boolean existsByCorreo(String email);

    Citizen getCitizenByIdUsuario(Integer citizenId);
}
