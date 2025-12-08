package com.IngSoftware.proyectosgr.domain.repository;

import com.IngSoftware.proyectosgr.domain.model.TipoActividad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoActividadRepository extends JpaRepository<TipoActividad, Integer> {

    List<TipoActividad> findByDescripcion(String descripcion);

    Optional<TipoActividad> findByTipotrabajo(String tipotrabajo);

}
