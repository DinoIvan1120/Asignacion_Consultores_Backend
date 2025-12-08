package com.IngSoftware.proyectosgr.service;

import com.IngSoftware.proyectosgr.domain.model.EstadoRequerimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EstadoRequerimientoService {

    List<EstadoRequerimiento> getAll();

    Page<EstadoRequerimiento> getAll(Pageable pageable);

    List<EstadoRequerimiento> getAllOrdered();

    EstadoRequerimiento getById(Integer id);

    EstadoRequerimiento getByDescripcion(String descripcion);

    EstadoRequerimiento getByAbr(String abr);

    EstadoRequerimiento create(EstadoRequerimiento estadoRequerimiento);

    EstadoRequerimiento update(Integer id, EstadoRequerimiento estadoRequerimiento);

    EstadoRequerimiento delete(Integer id);
}
