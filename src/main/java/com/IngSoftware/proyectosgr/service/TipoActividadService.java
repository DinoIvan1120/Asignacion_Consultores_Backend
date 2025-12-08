package com.IngSoftware.proyectosgr.service;

import com.IngSoftware.proyectosgr.domain.model.Empresa;
import com.IngSoftware.proyectosgr.domain.model.TipoActividad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TipoActividadService {

    List<TipoActividad> getAll();

    Page<TipoActividad> getAll(Pageable pageable);

    TipoActividad getById(Integer id);

    List<TipoActividad> getByDescripcion(String descripcion);

    TipoActividad getByTipoTrabajo(String tipotrabajo);

    TipoActividad create(TipoActividad tipoActividad);

    TipoActividad update(Integer id, TipoActividad tipoActividad);

    TipoActividad delete(Integer id);
}
