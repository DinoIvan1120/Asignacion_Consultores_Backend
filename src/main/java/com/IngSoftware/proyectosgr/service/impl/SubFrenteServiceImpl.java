package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.domain.model.SubFrente;
import com.IngSoftware.proyectosgr.domain.repository.SubFrenteRepository;
import com.IngSoftware.proyectosgr.service.SubFrenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubFrenteServiceImpl implements SubFrenteService {

    @Autowired
    private SubFrenteRepository subFrenteRepository;

    @Override
    public List<SubFrente> getAllSubFrentes() {
        return subFrenteRepository.findAll();
    }

    @Override
    public List<SubFrente> getAllSubFrentesByDescripcion() {
        return subFrenteRepository.findAll();
    }
}
