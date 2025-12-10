package com.IngSoftware.proyectosgr.service;

import com.IngSoftware.proyectosgr.domain.model.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmpresaService {

    List<Empresa> getAll();

    Page<Empresa> getAll(Pageable pageable);

    Empresa getById(Integer id);

    List<Empresa> getByEstado(Boolean estado);

    Empresa getByRuc(String ruc);

    Empresa getByIdempresaSap(String idempresaSap);

    List<Empresa> getByEmpresaPadre(Integer empresaPadre);

    List<Empresa> getNombresComerciales();

    Empresa create(Empresa empresa);

    Empresa update(Integer id, Empresa empresa);

    Empresa delete(Integer id);

    Empresa getByIdWithMoneda(Integer id);

    // 🔥 NUEVO: Obtener empresa con países
    Empresa getByIdWithPaises(Integer id);
}
