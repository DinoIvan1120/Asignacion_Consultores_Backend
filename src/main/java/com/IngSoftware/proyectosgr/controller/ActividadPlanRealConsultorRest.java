package com.IngSoftware.proyectosgr.controller;

import com.IngSoftware.proyectosgr.domain.dto.ActividadPlanRealConsultor.ActividadPlanRealConsultorResource;
import com.IngSoftware.proyectosgr.domain.dto.ActividadPlanRealConsultor.CreateActividadPlanRealConsultorResource;
import com.IngSoftware.proyectosgr.domain.dto.ActividadPlanRealConsultor.UpdateActividadPlanRealConsultorResource;
import com.IngSoftware.proyectosgr.domain.mapping.ActividadPlanRealConsultorMapper;
import com.IngSoftware.proyectosgr.domain.model.ActividadesPlanRealConsultor;
import com.IngSoftware.proyectosgr.service.ActividadPlanRealConsultorService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/requerimientos/{idrequerimiento}/actividades")

public class ActividadPlanRealConsultorRest {

    @Autowired
    private ActividadPlanRealConsultorService actividadService;

    @Autowired
    private ActividadPlanRealConsultorMapper mapper;

    /**
     * Búsqueda con filtros múltiples
     */
    @Operation(summary = "Buscar actividades con filtros",
            description = "Permite filtrar actividades de un requerimiento usando múltiples criterios opcionales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividades filtradas obtenidas exitosamente")
    })
    @GetMapping("/search")
    public ResponseEntity<Page<ActividadPlanRealConsultorResource>> searchActividades(
            @PathVariable Integer idrequerimiento,
            @RequestParam(required = false) Integer idusuario,
            @RequestParam(required = false) Integer idtipoactividad,
            @RequestParam(required = false) Boolean facturable,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            Pageable pageable) {

        Page<ActividadesPlanRealConsultor> actividades = actividadService.searchByFilters(
                idrequerimiento,
                idusuario,
                idtipoactividad,
                facturable,
                fechaInicio,
                fechaFin,
                pageable
        );

        Page<ActividadPlanRealConsultorResource> resources = actividades.map(
                actividad -> mapper.toResource(actividad)
        );

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    /**
     * Obtener todas las actividades de un requerimiento
     */
    @Operation(summary = "Obtener todas las actividades de un requerimiento",
            description = "Retorna una lista con todas las actividades del requerimiento especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de actividades obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<ActividadPlanRealConsultorResource>> getAllActividades(
            @PathVariable Integer idrequerimiento) {
        List<ActividadesPlanRealConsultor> actividades = actividadService.getAllByRequerimiento(idrequerimiento);
        List<ActividadPlanRealConsultorResource> resources = mapper.modelListToList(actividades);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    /**
     * Obtener actividades con paginación
     */
    @Operation(summary = "Obtener actividades con paginación",
            description = "Retorna una página con las actividades del requerimiento especificado")
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Página de actividades obtenida exitosamente")
        })
    @GetMapping("/page")
    public ResponseEntity<Page<ActividadPlanRealConsultorResource>> getAllActividadesPage(
            @PathVariable Integer idrequerimiento,
            Pageable pageable) {
        Page<ActividadesPlanRealConsultor> actividades = actividadService.getAllByRequerimiento(idrequerimiento, pageable);
        Page<ActividadPlanRealConsultorResource> resources = actividades.map(actividad -> mapper.toResource(actividad));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    /**
     * Obtener actividad por ID
     */
    @Operation(summary = "Obtener actividad por ID",
            description = "Retorna una actividad según su ID (solo si pertenece al requerimiento especificado)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad encontrada"),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActividadPlanRealConsultorResource> getActividadById(
            @PathVariable Integer idrequerimiento,
            @PathVariable Integer id) {
        ActividadesPlanRealConsultor actividad = actividadService.getById(id, idrequerimiento);
        ActividadPlanRealConsultorResource resource = mapper.toResource(actividad);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Obtener actividades facturables
     */
    @Operation(summary = "Obtener actividades facturables",
            description = "Retorna actividades filtradas por facturable del requerimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de actividades facturables")
    })
    @GetMapping("/facturable/{facturable}")
    public ResponseEntity<List<ActividadPlanRealConsultorResource>> getActividadesByFacturable(
            @PathVariable Integer idrequerimiento,
            @PathVariable Boolean facturable) {
        List<ActividadesPlanRealConsultor> actividades = actividadService.getByFacturable(idrequerimiento, facturable);
        List<ActividadPlanRealConsultorResource> resources = mapper.modelListToList(actividades);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    /**
     * Crear nueva actividad
     */
    @Operation(summary = "Crear nueva actividad",
            description = "Crea una nueva actividad para el requerimiento especificado. " +
                    "Campos obligatorios: idusuario, fechainicio, fechafin, idtipoactividad, tiemporegular, costo. " +
                    "El backend genera automáticamente: idmoneda, idescalatiempo, modo, valores por defecto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Actividad creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos - Revise que los campos obligatorios estén presentes"),
            @ApiResponse(responseCode = "404", description = "Requerimiento no encontrado")
    })
    @PostMapping
    public ResponseEntity<ActividadPlanRealConsultorResource> createActividad(
            @PathVariable Integer idrequerimiento,
            @Valid @RequestBody CreateActividadPlanRealConsultorResource resource) {

        // 1. Convertir DTO → Entidad
        ActividadesPlanRealConsultor actividad = mapper.toModel(resource);

        // 2. Crear actividad (aquí se genera TODO automáticamente)
        ActividadesPlanRealConsultor nuevaActividad = actividadService.create(actividad, idrequerimiento);

        // 3. Convertir Entidad → DTO de respuesta
        ActividadPlanRealConsultorResource actividadResource = mapper.toResource(nuevaActividad);

        // 4. Retornar con status 201 CREATED
        return new ResponseEntity<>(actividadResource, HttpStatus.CREATED);
    }

    /**
     * Actualizar actividad
     */
    @Operation(summary = "Actualizar actividad",
            description = "Actualiza los datos de una actividad existente (solo si pertenece al requerimiento especificado)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ActividadPlanRealConsultorResource> updateActividad(
            @PathVariable Integer idrequerimiento,
            @PathVariable Integer id,
            @Valid @RequestBody UpdateActividadPlanRealConsultorResource resource) {
        ActividadesPlanRealConsultor actividad = mapper.toModel(resource);
        ActividadesPlanRealConsultor updatedActividad = actividadService.update(id, actividad, idrequerimiento);
        ActividadPlanRealConsultorResource actividadResource = mapper.toResource(updatedActividad);
        return new ResponseEntity<>(actividadResource, HttpStatus.OK);
    }

    /**
     * Eliminar actividad
     */
    @Operation(summary = "Eliminar actividad",
            description = "Elimina una actividad del sistema (solo si pertenece al requerimiento especificado)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ActividadPlanRealConsultorResource> deleteActividad(
            @PathVariable Integer idrequerimiento,
            @PathVariable Integer id) {
        ActividadesPlanRealConsultor actividad = actividadService.delete(id, idrequerimiento);
        ActividadPlanRealConsultorResource resource = mapper.toResource(actividad);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
