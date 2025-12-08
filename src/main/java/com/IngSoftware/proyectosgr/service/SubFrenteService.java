package com.IngSoftware.proyectosgr.service;

import com.IngSoftware.proyectosgr.domain.model.Moneda;
import com.IngSoftware.proyectosgr.domain.model.SubFrente;

import java.util.List;

public interface SubFrenteService {

    List<SubFrente> getAllSubFrentes();

    List<SubFrente> getAllSubFrentesByDescripcion();
}
