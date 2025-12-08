package com.IngSoftware.proyectosgr.controller;

import com.IngSoftware.proyectosgr.domain.dto.Citizen.CitizenResource;
import com.IngSoftware.proyectosgr.domain.dto.Citizen.CreateCitizenResource;
import com.IngSoftware.proyectosgr.domain.dto.Citizen.UpdateCitizenResource;
import com.IngSoftware.proyectosgr.domain.mapping.CitizenMapper;
import com.IngSoftware.proyectosgr.domain.model.Citizen;
import com.IngSoftware.proyectosgr.service.CitizenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/citizen")
public class CitizenRest {
    @Autowired
    private CitizenService citizenService;

    @Autowired
    private CitizenMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(path = "/saveCitizen", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CitizenResource> saveCitizen(@RequestBody CreateCitizenResource resource) throws Exception {
        Citizen model = mapper.toModel(resource);
        Citizen saved = this.citizenService.save(model,resource);
        //CitizenResource citizen = mapper.toResource(this.citizenService.save(mapper.toModel(resource)));
        return new ResponseEntity<>(mapper.toResource(saved), HttpStatus.OK);
    }

    @PostMapping(path = "/updateCitizen/{citizenId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CitizenResource> updateCitizen(@RequestBody UpdateCitizenResource resource, @PathVariable Integer citizenId) throws Exception{
        CitizenResource citizen = mapper.toResource(this.citizenService.update(mapper.toModel(resource), citizenId));
        return new ResponseEntity<>(citizen, HttpStatus.OK);
    }

    @GetMapping(path = "/getCitizenById/{citizenId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CitizenResource> getCitizenById(@PathVariable Integer citizenId) throws Exception {
        CitizenResource citizen = mapper.toResource(this.citizenService.getCitizenById(citizenId));
        return new ResponseEntity<>(citizen, HttpStatus.OK);
    }

    @GetMapping(path = "/getAllCitizen", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<CitizenResource>> getAllCitizen() throws Exception {
        List<CitizenResource> citizenList = mapper.modelListToList(this.citizenService.getAllCitizen());
        return new ResponseEntity<>(citizenList, HttpStatus.OK);
    }

}
