package com.IngSoftware.proyectosgr.service;

import com.IngSoftware.proyectosgr.domain.model.Moneda;

import java.util.List;

public interface MonedaService {

    List<Moneda> getAllMonedas();

    List<Moneda> getByDescripcion(String descripcion);

    List<Moneda> getAllMonedasByDescripcion();
}
