package com.IngSoftware.proyectosgr.controller;


import com.IngSoftware.proyectosgr.domain.dto.Empresa.EmpresaResource;
import com.IngSoftware.proyectosgr.domain.dto.Moneda.MonedaResource;
import com.IngSoftware.proyectosgr.domain.dto.Moneda.MonedaResourceDescription;
import com.IngSoftware.proyectosgr.domain.mapping.MonedaMapper;
import com.IngSoftware.proyectosgr.domain.model.Empresa;
import com.IngSoftware.proyectosgr.domain.model.Moneda;
import com.IngSoftware.proyectosgr.service.MonedaService;
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
@RequestMapping("/api/v1/monedas")
public class MonedaRest {

    @Autowired
    private MonedaService monedaService;

    @Autowired
    private MonedaMapper monedaMapper;

    @Operation(summary = "Obtener todas las monedas", description = "Retorna una lista con todas las monedas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de monedas obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaResource.class)))
    })
    @GetMapping
    public ResponseEntity<List<MonedaResource>> getAllMonedas() {
        List<Moneda> monedas = monedaService.getAllMonedas();
        List<MonedaResource> resources = monedaMapper.modelListToList(monedas);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }


    @Operation(summary = "Obtener todas las monedas por descripción", description = "Retorna una lista con todas las monedas con descripción registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de monedas con descripción obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaResource.class)))
    })
    @GetMapping("/descripcion")
    public ResponseEntity<List<MonedaResourceDescription>> getAllMonedasDescripcion() {
        List<Moneda> monedas = monedaService.getAllMonedasByDescripcion();
        List<MonedaResourceDescription> resources = monedaMapper.modelListToDescripcionList(monedas);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

}
