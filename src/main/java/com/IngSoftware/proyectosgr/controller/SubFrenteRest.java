package com.IngSoftware.proyectosgr.controller;

import com.IngSoftware.proyectosgr.domain.dto.Empresa.EmpresaResource;
import com.IngSoftware.proyectosgr.domain.dto.SubFrente.SubFrenteDescripcionResource;
import com.IngSoftware.proyectosgr.domain.dto.SubFrente.SubFrenteResource;
import com.IngSoftware.proyectosgr.domain.mapping.SubFrenteMapper;
import com.IngSoftware.proyectosgr.domain.model.SubFrente;
import com.IngSoftware.proyectosgr.service.SubFrenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subfrentes")
public class SubFrenteRest {

    @Autowired
    private SubFrenteService subFrenteService;

    @Autowired
    private SubFrenteMapper subFrenteMapper;

    @Operation(summary = "Obtener todas los subfrentes", description = "Retorna una lista con todos los subfrentes registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de subfrentes obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaResource.class)))
    })
    @GetMapping
    public ResponseEntity<List<SubFrenteResource>> getAllMonedas() {
        List<SubFrente> subFrentes = subFrenteService.getAllSubFrentes();
        List<SubFrenteResource> resources = subFrenteMapper.modelListToList(subFrentes);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }


    @Operation(summary = "Obtener todas los subfrentes por descripción", description = "Retorna una lista con todos las subfrentes con descripción registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de subfrentes con descripción obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaResource.class)))
    })
    @GetMapping("/descripcion")
    public ResponseEntity<List<SubFrenteDescripcionResource>> getAllSubFrentesDescripcion() {
        List<SubFrente> subFrentes = subFrenteService.getAllSubFrentesByDescripcion();
        List<SubFrenteDescripcionResource> resources = subFrenteMapper.modelListToDescripcionList(subFrentes);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}
