package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.config.exception.EmailException;
import com.IngSoftware.proyectosgr.config.exception.ResourceNotFoundException;
import com.IngSoftware.proyectosgr.domain.dto.Citizen.CreateCitizenResource;
import com.IngSoftware.proyectosgr.domain.model.Citizen;
import com.IngSoftware.proyectosgr.domain.repository.CitizenRepository;
import com.IngSoftware.proyectosgr.service.CitizenService;
import com.IngSoftware.proyectosgr.util.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CitizenServiceImpl implements CitizenService {
    private final CitizenRepository citizenRepository;
    private final AuthServiceImpl authService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String ENTITY = "Citizen";
    public CitizenServiceImpl(CitizenRepository citizenRepository, AuthServiceImpl authService) {
        this.citizenRepository = citizenRepository;
        this.authService = authService;
    }

    @Override
    public Citizen save(Citizen citizen, CreateCitizenResource resource) throws Exception {
        Boolean validateEmail = DataUtils.validateEmail(citizen.getCorreo());
        if(!validateEmail) {
            throw new EmailException(citizen.getCorreo());
        }
        Boolean existAlias = this.citizenRepository.existsByAlias(citizen.getAlias());
        Boolean existEmail = this.citizenRepository.existsByCorreo(citizen.getCorreo());
        if(Boolean.TRUE.equals(existAlias)){
            throw new RuntimeException(String.format("Ya existe un usuario registrado con el alias %s", citizen.getAlias()));
        } else if(Boolean.TRUE.equals(existEmail)){
            throw new RuntimeException(String.format("Ya existe un usuario registrado con el email %s", citizen.getCorreo()));
        }
        citizen.setEstado(true);
        return this.authService.registerCitizen(citizen, resource);
    }

    @Override
    public Citizen update(Citizen req, Integer citizenId) throws Exception {
        Optional<Citizen> optionalCitizen = this.citizenRepository.findById(citizenId);
        if(optionalCitizen.isEmpty()){
            throw new ResourceNotFoundException(ENTITY, citizenId);
        }
        Boolean existAlias = this.citizenRepository.existsByAlias(req.getAlias());
        if(Boolean.TRUE.equals(existAlias)){
            throw new RuntimeException(String.format("Ya existe un usuario registrado con el alias %s", req.getAlias()));
        }
        Citizen citizen = optionalCitizen.get();
        citizen.setAlias(req.getAlias());
        return this.citizenRepository.save(citizen);
    }

    @Override
    public Citizen getCitizenById(Integer citizenId) throws ResourceNotFoundException {
        Optional<Citizen> citizen = this.citizenRepository.findById(citizenId);
        if(citizen.isEmpty()){
            throw new ResourceNotFoundException(ENTITY, citizenId);
        }
        return citizen.get();
    }

    @Override
    public List<Citizen> getAllCitizen() throws RuntimeException {
        List<Citizen> citizenList = this.citizenRepository.findAll();
        if(citizenList.isEmpty()){
            throw new RuntimeException("No se encontró ningun citizen");
        }
        return citizenList;
    }

}
