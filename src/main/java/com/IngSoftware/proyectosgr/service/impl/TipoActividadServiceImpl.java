package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.config.exception.ResourceNotFoundException;
import com.IngSoftware.proyectosgr.domain.model.Empresa;
import com.IngSoftware.proyectosgr.domain.model.TipoActividad;
import com.IngSoftware.proyectosgr.domain.repository.TipoActividadRepository;
import com.IngSoftware.proyectosgr.service.TipoActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoActividadServiceImpl implements TipoActividadService {

    @Autowired
    TipoActividadRepository tipoActividadRepository;

    @Override
    public List<TipoActividad> getAll() {
        return tipoActividadRepository.findAll();
    }

    @Override
    public Page<TipoActividad> getAll(Pageable pageable) {
        return tipoActividadRepository.findAll(pageable);
    }

    @Override
    public TipoActividad getById(Integer id) {
        return tipoActividadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoActividad ->", "id = " + id));
    }


    @Override
    public List<TipoActividad> getByDescripcion(String descripcion) {
        return tipoActividadRepository.findByDescripcion(descripcion);
    }

    @Override
    public TipoActividad getByTipoTrabajo(String tipotrabajo) {
        return tipoActividadRepository.findByTipotrabajo(tipotrabajo)
                .orElseThrow(() -> new ResourceNotFoundException("TipoActividad", "tipotrabajo ->", tipotrabajo));
    }

    @Override
    public TipoActividad create(TipoActividad tipoActividad) {
        return tipoActividadRepository.save(tipoActividad);
    }

    @Override
    public TipoActividad update(Integer id, TipoActividad tipoActividad) {

        TipoActividad tipoActividadupdate = getById(id);
        tipoActividadupdate.setId(id);
        tipoActividadupdate.setDescripcion(tipoActividad.getDescripcion());
        tipoActividadupdate.setTipotrabajo(tipoActividad.getTipotrabajo());
        tipoActividadupdate.setMostrar(tipoActividad.getMostrar());
        tipoActividadupdate.setTipoTrabajoReporte(tipoActividad.getTipoTrabajoReporte());
        return tipoActividadRepository.save(tipoActividadupdate);
    }

    @Override
    public TipoActividad delete(Integer id) {
        TipoActividad tipoActividad = getById(id);
        tipoActividadRepository.delete(tipoActividad);
        return tipoActividad;
    }
}
