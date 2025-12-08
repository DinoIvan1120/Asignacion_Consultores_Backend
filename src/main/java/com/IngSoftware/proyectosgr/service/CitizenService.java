package com.IngSoftware.proyectosgr.service;

import com.IngSoftware.proyectosgr.domain.dto.Citizen.CreateCitizenResource;
import com.IngSoftware.proyectosgr.domain.model.Citizen;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CitizenService {
    Citizen save(Citizen citizen, CreateCitizenResource resource) throws Exception;
    Citizen update(Citizen citizen, Integer citizenId) throws Exception;
    Citizen getCitizenById(Integer citizenId) throws Exception;
    List<Citizen> getAllCitizen() throws Exception;
}
