package com.IngSoftware.proyectosgr.controller;

import com.IngSoftware.proyectosgr.domain.dto.Asignacion.AsignacionResource;
import com.IngSoftware.proyectosgr.domain.dto.Requerimiento.*;
import com.IngSoftware.proyectosgr.domain.mapping.RequerimientoMapper;
import com.IngSoftware.proyectosgr.domain.model.Requerimiento;
import com.IngSoftware.proyectosgr.service.AsignacionService;
import com.IngSoftware.proyectosgr.service.RequerimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/requerimientos")
public class RequerimientoRest {

    @Autowired
    private RequerimientoService requerimientoService;

    @Autowired
    private RequerimientoMapper mapper;

    @Autowired
    private AsignacionService asignacionService;


    /**
     * ✅ NUEVO ENDPOINT: Búsqueda con filtros múltiples
     * Todos los parámetros son opcionales excepto la paginación
     */
    /*
    @Operation(summary = "Buscar requerimientos con filtros",
            description = "Permite filtrar requerimientos del coordinador autenticado usando múltiples criterios opcionales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requerimientos filtrados obtenidos exitosamente")
    })
    @GetMapping("/search")
    public ResponseEntity<Page<RequerimientoResource>> searchRequerimientos(
            @RequestParam(required = false) Integer idEmpresa,
            @RequestParam(required = false) String codRequerimiento,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam(required = false) Integer idUsuario,
            @RequestParam(required = false) Integer idRequerimiento,
            @RequestParam(required = false) Integer idEstadoRequerimiento,
            // En parámetros del endpoint:
            @RequestParam(required = false) String nombreConsultor,
            Pageable pageable) {

        Page<Requerimiento> requerimientos = requerimientoService.searchByFilters(
                idEmpresa,
                codRequerimiento,
                fechaInicio,
                fechaFin,
                idUsuario,
                idRequerimiento,
                idEstadoRequerimiento,
                nombreConsultor,
                pageable
        );

        // Usar map() para preservar la información de paginación
        Page<RequerimientoResource> resources = requerimientos.map(
                requerimiento -> mapper.toResource(requerimiento)
        );

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
     */

    @Operation(summary = "Buscar requerimientos con filtros",
            description = "Permite filtrar requerimientos del coordinador autenticado usando múltiples criterios opcionales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requerimientos filtrados obtenidos exitosamente")
    })
    @GetMapping("/search")
    public ResponseEntity<Page<AsignacionResource>> searchRequerimientos(
            @Parameter(description = "Fecha de inicio del requerimiento (formato: yyyy-MM-dd)", example = "2025-01-01")
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date fechaInicio,

            @Parameter(description = "Fecha de fin del requerimiento (formato: yyyy-MM-dd)", example = "2025-12-31")
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date fechaFin,

            @Parameter(description = "ID del cliente (usuario del requerimiento)", example = "123")
            @RequestParam(required = false)
            Integer idUsuario,

            @Parameter(description = "Nombre del consultor (busca en el título del requerimiento)", example = "Juan")
            @RequestParam(required = false)
            String nombreConsultor,

            @Parameter(description = "Id de la empresa", example = "CSTI")
            @RequestParam(required = false)
            Integer idEmpresa,

            @Parameter(description = "Código de requeirimiento", example = "CSTI-2025-0001")
            @RequestParam(required = false)
            String codRequerimiento,

            @Parameter(description = "Id de requeirimiento", example = "56700")
            @RequestParam(required = false)
            Integer idRequerimiento,

            @Parameter(description = "Id de Estadorequeirimiento", example = "En ejecución")
            @RequestParam(required = false)
            Integer idEstadoRequerimiento,

            @Parameter(description = "Parámetros de paginación (page, size, sort)", hidden = true)
            Pageable pageable) {

        Page<AsignacionResource> asignaciones = asignacionService.searchAsignacionesByFilters(
                fechaInicio,
                fechaFin,
                idUsuario,
                nombreConsultor,
                idEmpresa,
                codRequerimiento,
                idRequerimiento,
                idEstadoRequerimiento,
                pageable
        );

        return new ResponseEntity<>(asignaciones, HttpStatus.OK);
    }

    @Operation(summary = "Obtener todos los requerimientos del coordinador autenticado",
            description = "Retorna una lista con todos los requerimientos del coordinador que ha iniciado sesión")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de requerimientos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RequerimientoResource.class)))
    })
    @GetMapping
    public ResponseEntity<List<RequerimientoResource>> getAllRequerimientos() {
        List<Requerimiento> requerimientos = requerimientoService.getAllByCoordinador();
        List<RequerimientoResource> resources = mapper.modelListToList(requerimientos);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener requerimientos con paginación",
            description = "Retorna una página con los requerimientos del coordinador autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de requerimientos obtenida exitosamente")
    })

    @GetMapping("/page")
    public ResponseEntity<Page<RequerimientoResource>> getAllRequerimientosPage(Pageable pageable) {
        Page<Requerimiento> requerimientos = requerimientoService.getAllByCoordinador(pageable);
        // Usar map() para preservar la información de paginación (totalElements, totalPages, etc)
        Page<RequerimientoResource> resources = requerimientos.map(requerimiento -> mapper.toResource(requerimiento));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }


    /*
    @GetMapping("/page")
    public ResponseEntity<Page<RequerimientoResource>> getAllRequerimientosPage(Pageable pageable) {
        Page<Requerimiento> requerimientos = requerimientoService.getAllByCoordinador(pageable);
        Page<RequerimientoResource> resources = mapper.modelListToPage(requerimientos.getContent(), pageable);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

     */


    @Operation(summary = "Obtener requerimiento por ID",
            description = "Retorna un requerimiento según su ID (solo si pertenece al coordinador autenticado)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requerimiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Requerimiento no encontrado o no tiene permisos")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RequerimientoResource> getRequerimientoById(@PathVariable Integer id) {
        Requerimiento requerimiento = requerimientoService.getById(id);
        RequerimientoResource resource = mapper.toResource(requerimiento);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @Operation(summary = "Obtener solo ID y código de requerimientos del coordinador",
            description = "Retorna una página que contiene únicamente idRequerimiento y codRequerimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página obtenida exitosamente")
    })
    @GetMapping("/codigos/page")
    public ResponseEntity<Page<RequerimientoCodigoResource>> getCodigosRequerimientos(Pageable pageable) {

        Page<Requerimiento> requerimientos = requerimientoService.getCodigosByCoordinador(pageable);

        Page<RequerimientoCodigoResource> codes = requerimientos.map(req ->
                new RequerimientoCodigoResource(
                        req.getIdRequerimiento(),
                        req.getCodRequerimiento()
                )
        );

        return new ResponseEntity<>(codes, HttpStatus.OK);
    }

    @Operation(summary = "Obtener solo ID requerimientos del coordinador",
            description = "Retorna una página que contiene únicamente idRequerimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página obtenida exitosamente")
    })
    @GetMapping("/codigoId/page")
    public ResponseEntity<Page<RequerimientoIdCodigoResource>> gellAllIdRequerimiento(Pageable pageable) {

        Page<Requerimiento> requerimientos = requerimientoService.searchByFiltersIdCodigoRequerimiento(pageable);

        Page<RequerimientoIdCodigoResource> codes = requerimientos.map(req ->
                new RequerimientoIdCodigoResource(
                        req.getIdRequerimiento()
                )
        );

        return new ResponseEntity<>(codes, HttpStatus.OK);
    }

    @Operation(summary = "Obtener requerimiento por código",
            description = "Retorna un requerimiento según su código (solo si pertenece al coordinador autenticado)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requerimiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Requerimiento no encontrado")
    })
    @GetMapping("/codigo/{codRequerimiento}")
    public ResponseEntity<RequerimientoResource> getRequerimientoByCodigo(@PathVariable String codRequerimiento) {
        Requerimiento requerimiento = requerimientoService.getByCodRequerimiento(codRequerimiento);
        RequerimientoResource resource = mapper.toResource(requerimiento);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @Operation(summary = "Obtener requerimientos por estado",
            description = "Retorna requerimientos filtrados por estado del coordinador autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de requerimientos por estado")
    })
    @GetMapping("/estado/{idEstadoRequerimiento}")
    public ResponseEntity<List<RequerimientoResource>> getRequerimientosByEstado(
            @PathVariable Integer idEstadoRequerimiento) {
        List<Requerimiento> requerimientos = requerimientoService.getByEstadoRequerimiento(idEstadoRequerimiento);
        List<RequerimientoResource> resources = mapper.modelListToList(requerimientos);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener requerimientos por empresa",
            description = "Retorna requerimientos filtrados por empresa del coordinador autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de requerimientos por empresa")
    })
    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<RequerimientoResource>> getRequerimientosByEmpresa(
            @PathVariable Integer idEmpresa) {
        List<Requerimiento> requerimientos = requerimientoService.getByEmpresa(idEmpresa);
        List<RequerimientoResource> resources = mapper.modelListToList(requerimientos);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener requerimientos por prioridad",
            description = "Retorna requerimientos filtrados por prioridad del coordinador autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de requerimientos por prioridad")
    })
    @GetMapping("/prioridad/{idPrioridad}")
    public ResponseEntity<List<RequerimientoResource>> getRequerimientosByPrioridad(
            @PathVariable Integer idPrioridad) {
        List<Requerimiento> requerimientos = requerimientoService.getByPrioridad(idPrioridad);
        List<RequerimientoResource> resources = mapper.modelListToList(requerimientos);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener requerimientos urgentes",
            description = "Retorna requerimientos filtrados por urgencia del coordinador autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de requerimientos urgentes")
    })
    @GetMapping("/urgente/{urgente}")
    public ResponseEntity<List<RequerimientoResource>> getRequerimientosByUrgente(
            @PathVariable Boolean urgente) {
        List<Requerimiento> requerimientos = requerimientoService.getByUrgente(urgente);
        List<RequerimientoResource> resources = mapper.modelListToList(requerimientos);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener requerimientos por subfrente",
            description = "Retorna requerimientos filtrados por subfrente del coordinador autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de requerimientos por subfrente")
    })
    @GetMapping("/subfrente/{idSubfrente}")
    public ResponseEntity<List<RequerimientoResource>> getRequerimientosBySubfrente(
            @PathVariable Integer idSubfrente) {
        List<Requerimiento> requerimientos = requerimientoService.getBySubfrente(idSubfrente);
        List<RequerimientoResource> resources = mapper.modelListToList(requerimientos);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Crear nuevo requerimiento",
            description = "Crea un nuevo requerimiento asignándolo automáticamente al coordinador autenticado. " +
                    "Solo requiere: título, idEmpresa, idSubfrente, idUsuario y campos opcionales. " +
                    "El backend genera automáticamente: código, país, estado, prioridad, fechas y defaults.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requerimiento creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos - Revise que los campos obligatorios estén presentes"),
            @ApiResponse(responseCode = "401", description = "No autenticado - Token JWT inválido o ausente"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    @PostMapping
    public ResponseEntity<RequerimientoResource> createRequerimiento(
            @Valid @RequestBody CreateRequerimientoResource resource) {

        // 1. Convertir DTO → Entidad (solo con los campos del frontend)
        Requerimiento requerimiento = mapper.toModel(resource);

        // 2. Crear requerimiento (aquí se genera TODO automáticamente)
        Requerimiento newRequerimiento = requerimientoService.create(requerimiento);

        // 3. Convertir Entidad → DTO de respuesta (con todas las relaciones)
        RequerimientoResource requerimientoResource = mapper.toResource(newRequerimiento);

        // 4. Retornar con status 201 CREATED
        return new ResponseEntity<>(requerimientoResource, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar requerimiento",
            description = "Actualiza los datos de un requerimiento existente (solo si pertenece al coordinador autenticado)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requerimiento actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Requerimiento no encontrado o no tiene permisos"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RequerimientoResource> updateRequerimiento(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateRequerimientoResource resource) {
        Requerimiento requerimiento = mapper.toModel(resource);
        Requerimiento updatedRequerimiento = requerimientoService.update(id, requerimiento);
        RequerimientoResource requerimientoResource = mapper.toResource(updatedRequerimiento);
        return new ResponseEntity<>(requerimientoResource, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar requerimiento",
            description = "Elimina un requerimiento del sistema (solo si pertenece al coordinador autenticado)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requerimiento eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Requerimiento no encontrado o no tiene permisos")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<RequerimientoResource> deleteRequerimiento(@PathVariable Integer id) {
        Requerimiento requerimiento = requerimientoService.delete(id);
        RequerimientoResource resource = mapper.toResource(requerimiento);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
