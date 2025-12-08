package com.IngSoftware.proyectosgr.domain.repository;

import com.IngSoftware.proyectosgr.domain.model.EstadoRequerimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstadoRequerimientoRepository extends JpaRepository<EstadoRequerimiento, Integer> {
    List<EstadoRequerimiento> findAllByOrderByOrdenAsc();

    Optional<EstadoRequerimiento> findByDescripcion(String descripcion);

    Optional<EstadoRequerimiento> findByAbr(String abr);

    boolean existsByDescripcion(String descripcion);
}
