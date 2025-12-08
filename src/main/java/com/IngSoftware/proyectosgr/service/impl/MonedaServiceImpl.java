package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.domain.model.Moneda;
import com.IngSoftware.proyectosgr.domain.repository.MonedaRespository;
import com.IngSoftware.proyectosgr.service.MonedaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonedaServiceImpl implements MonedaService {

    @Autowired
    private MonedaRespository monedaRespository;

    @Override
    public List<Moneda> getAllMonedas() {
        return monedaRespository.findAll();
    }

    @Override
    public List<Moneda> getAllMonedasByDescripcion() {
        return monedaRespository.findAll();
    }

    @Override
    public List<Moneda> getByDescripcion(String descripcion) {
        return monedaRespository.findByDescripcion(descripcion);
    }
}
