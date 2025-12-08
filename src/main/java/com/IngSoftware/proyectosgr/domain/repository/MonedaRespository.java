package com.IngSoftware.proyectosgr.domain.repository;

import com.IngSoftware.proyectosgr.domain.model.Moneda;
import com.IngSoftware.proyectosgr.domain.model.TipoActividad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonedaRespository extends JpaRepository<Moneda, Integer> {

    List<Moneda> findByDescripcion(String descripcion);
}
