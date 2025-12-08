package com.IngSoftware.proyectosgr.controller;


import com.IngSoftware.proyectosgr.domain.dto.EstadoRequerimiento.CreateEstadoRequerimientoResource;
import com.IngSoftware.proyectosgr.domain.dto.EstadoRequerimiento.EstadoRequerimientoResource;
import com.IngSoftware.proyectosgr.domain.dto.EstadoRequerimiento.UpdateEstadoRequerimientoResource;
import com.IngSoftware.proyectosgr.domain.mapping.EstadoRequerimientoMapper;
import com.IngSoftware.proyectosgr.domain.model.EstadoRequerimiento;
import com.IngSoftware.proyectosgr.service.EstadoRequerimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/estados-requerimiento")
public class EstadoRequerimientoRest {

    @Autowired
    private EstadoRequerimientoService estadoRequerimientoService;

    @Autowired
    private EstadoRequerimientoMapper mapper;

    @Operation(summary = "Obtener todos los estados de requerimiento",
            description = "Retorna una lista con todos los estados de requerimiento registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estados obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstadoRequerimientoResource.class)))
    })
    @GetMapping
    public ResponseEntity<List<EstadoRequerimientoResource>> getAllEstadosRequerimiento() {
        List<EstadoRequerimiento> estados = estadoRequerimientoService.getAll();
        List<EstadoRequerimientoResource> resources = mapper.modelListToList(estados);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener todos los estados ordenados",
            description = "Retorna una lista con todos los estados ordenados por el campo orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estados ordenados obtenida exitosamente")
    })
    @GetMapping("/ordenados")
    public ResponseEntity<List<EstadoRequerimientoResource>> getAllEstadosOrdenados() {
        List<EstadoRequerimiento> estados = estadoRequerimientoService.getAllOrdered();
        List<EstadoRequerimientoResource> resources = mapper.modelListToList(estados);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener todos los estados con paginación",
            description = "Retorna una página con los estados de requerimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de estados obtenida exitosamente")
    })
    @GetMapping("/page")
    public ResponseEntity<Page<EstadoRequerimientoResource>> getAllEstadosPage(Pageable pageable) {
        Page<EstadoRequerimiento> estados = estadoRequerimientoService.getAll(pageable);
        Page<EstadoRequerimientoResource> resources = mapper.modelListToPage(estados.getContent(), pageable);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener estado por ID",
            description = "Retorna un estado de requerimiento según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado encontrado"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstadoRequerimientoResource> getEstadoById(@PathVariable Integer id) {
        EstadoRequerimiento estado = estadoRequerimientoService.getById(id);
        EstadoRequerimientoResource resource = mapper.toResource(estado);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @Operation(summary = "Obtener estado por descripción",
            description = "Retorna un estado de requerimiento según su descripción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado encontrado"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado")
    })
    @GetMapping("/descripcion/{descripcion}")
    public ResponseEntity<EstadoRequerimientoResource> getEstadoByDescripcion(@PathVariable String descripcion) {
        EstadoRequerimiento estado = estadoRequerimientoService.getByDescripcion(descripcion);
        EstadoRequerimientoResource resource = mapper.toResource(estado);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @Operation(summary = "Obtener estado por abreviatura",
            description = "Retorna un estado de requerimiento según su abreviatura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado encontrado"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado")
    })
    @GetMapping("/abr/{abr}")
    public ResponseEntity<EstadoRequerimientoResource> getEstadoByAbr(@PathVariable String abr) {
        EstadoRequerimiento estado = estadoRequerimientoService.getByAbr(abr);
        EstadoRequerimientoResource resource = mapper.toResource(estado);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @Operation(summary = "Crear nuevo estado de requerimiento",
            description = "Crea un nuevo estado de requerimiento en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estado creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<EstadoRequerimientoResource> createEstado(
            @Valid @RequestBody CreateEstadoRequerimientoResource resource) {
        EstadoRequerimiento estado = mapper.toModel(resource);
        EstadoRequerimiento newEstado = estadoRequerimientoService.create(estado);
        EstadoRequerimientoResource estadoResource = mapper.toResource(newEstado);
        return new ResponseEntity<>(estadoResource, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar estado de requerimiento",
            description = "Actualiza los datos de un estado de requerimiento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstadoRequerimientoResource> updateEstado(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateEstadoRequerimientoResource resource) {
        EstadoRequerimiento estado = mapper.toModel(resource);
        EstadoRequerimiento updatedEstado = estadoRequerimientoService.update(id, estado);
        EstadoRequerimientoResource estadoResource = mapper.toResource(updatedEstado);
        return new ResponseEntity<>(estadoResource, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar estado de requerimiento",
            description = "Elimina un estado de requerimiento del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<EstadoRequerimientoResource> deleteEstado(@PathVariable Integer id) {
        EstadoRequerimiento estado = estadoRequerimientoService.delete(id);
        EstadoRequerimientoResource resource = mapper.toResource(estado);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
