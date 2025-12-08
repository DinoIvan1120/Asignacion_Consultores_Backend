package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.config.exception.ResourceNotFoundException;
import com.IngSoftware.proyectosgr.domain.model.EstadoRequerimiento;
import com.IngSoftware.proyectosgr.domain.repository.EstadoRequerimientoRepository;
import com.IngSoftware.proyectosgr.service.EstadoRequerimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstadoRequerimientoServiceImpl implements EstadoRequerimientoService {

    @Autowired
    private EstadoRequerimientoRepository estadoRequerimientoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EstadoRequerimiento> getAll() {
        return estadoRequerimientoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstadoRequerimiento> getAll(Pageable pageable) {
        return estadoRequerimientoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoRequerimiento> getAllOrdered() {
        return estadoRequerimientoRepository.findAllByOrderByOrdenAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoRequerimiento getById(Integer id) {
        return estadoRequerimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "EstadoRequerimiento", "idEstadoRequerimiento" +  id));
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoRequerimiento getByDescripcion(String descripcion) {
        return estadoRequerimientoRepository.findByDescripcion(descripcion)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "EstadoRequerimiento", "descripcion", descripcion));
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoRequerimiento getByAbr(String abr) {
        return estadoRequerimientoRepository.findByAbr(abr)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "EstadoRequerimiento", "abr", abr));
    }

    @Override
    @Transactional
    public EstadoRequerimiento create(EstadoRequerimiento estadoRequerimiento) {
        // Validar que no exista un estado con la misma descripción
        if (estadoRequerimientoRepository.existsByDescripcion(estadoRequerimiento.getDescripcion())) {
            throw new IllegalArgumentException(
                    "Ya existe un Estado de Requerimiento con la descripción: " +
                            estadoRequerimiento.getDescripcion());
        }

        return estadoRequerimientoRepository.save(estadoRequerimiento);
    }

    @Override
    @Transactional
    public EstadoRequerimiento update(Integer id, EstadoRequerimiento estadoRequerimiento) {
        EstadoRequerimiento existingEstado = estadoRequerimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "EstadoRequerimiento", "idEstadoRequerimiento" + id));

        // Validar que no exista otro estado con la misma descripción
        estadoRequerimientoRepository.findByDescripcion(estadoRequerimiento.getDescripcion())
                .ifPresent(estado -> {
                    if (!estado.getIdEstadoRequerimiento().equals(id)) {
                        throw new IllegalArgumentException(
                                "Ya existe otro Estado de Requerimiento con la descripción: " +
                                        estadoRequerimiento.getDescripcion());
                    }
                });

        existingEstado.setDescripcion(estadoRequerimiento.getDescripcion());
        existingEstado.setOrden(estadoRequerimiento.getOrden());
        existingEstado.setAbr(estadoRequerimiento.getAbr());

        return estadoRequerimientoRepository.save(existingEstado);
    }

    @Override
    @Transactional
    public EstadoRequerimiento delete(Integer id) {
        EstadoRequerimiento estadoRequerimiento = estadoRequerimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "EstadoRequerimiento", "idEstadoRequerimiento" + id));

        estadoRequerimientoRepository.delete(estadoRequerimiento);
        return estadoRequerimiento;
    }
}
