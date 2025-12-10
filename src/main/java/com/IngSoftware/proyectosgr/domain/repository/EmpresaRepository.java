package com.IngSoftware.proyectosgr.domain.repository;

import com.IngSoftware.proyectosgr.domain.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    // Método para obtener empresa con sus países (fetch eager)
    @Query("SELECT e FROM Empresa e LEFT JOIN FETCH e.paises WHERE e.id = :id")
    Optional<Empresa> findByIdWithPaises(@Param("id") Integer id);

    @Query("SELECT e FROM Empresa e JOIN FETCH e.moneda WHERE e.id = :id")
    Optional<Empresa> findByIdWithMoneda(@Param("id") Integer id);

    List<Empresa> findByEstado(Boolean estado);

    Optional<Empresa> findByRuc(String ruc);

    Optional<Empresa> findByIdempresaSap(String idempresaSap);

    List<Empresa> findByEmpresaPadre(Integer empresaPadre);
}
