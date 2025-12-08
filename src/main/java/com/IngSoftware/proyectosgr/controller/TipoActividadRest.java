package com.IngSoftware.proyectosgr.controller;

import com.IngSoftware.proyectosgr.domain.dto.Empresa.EmpresaResource;
import com.IngSoftware.proyectosgr.domain.dto.Requerimiento.RequerimientoResource;
import com.IngSoftware.proyectosgr.domain.dto.TipoActividad.TipoActividadDescripcionResource;
import com.IngSoftware.proyectosgr.domain.dto.TipoActividad.TipoActividadResource;
import com.IngSoftware.proyectosgr.domain.mapping.TipoActividadMapper;
import com.IngSoftware.proyectosgr.domain.model.Empresa;
import com.IngSoftware.proyectosgr.domain.model.TipoActividad;
import com.IngSoftware.proyectosgr.service.TipoActividadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipoactividad")
public class TipoActividadRest {

    @Autowired
    TipoActividadService tipoActividadService;

    @Autowired
    TipoActividadMapper tipoActividadMapper;

    @Operation(summary = "Obtener todos los tipos de actividades", description = "Retorna una lista con todas las actividades registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tipo de actividades obtenidas exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaResource.class)))
    })
    @GetMapping("/descripcion")
    public ResponseEntity<List<TipoActividadDescripcionResource>> getAllTipoActividad() {
        List<TipoActividad> tipoActividads = tipoActividadService.getAll();
        List<TipoActividadDescripcionResource> resources = tipoActividadMapper.modelListToDescripcionList(tipoActividads);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener todas los tipos de actividades con paginación", description = "Retorna una página con los tipos de actividades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de tipos de actividades obtenida exitosamente")
    })
    @GetMapping("/page")
    public ResponseEntity<Page<TipoActividadResource>> getAllTipoDeActividadesPage(Pageable pageable) {
        Page<TipoActividad> tipoActividads = tipoActividadService.getAll(pageable);
        Page<TipoActividadResource> resources = tipoActividads.map(tipoActividad -> tipoActividadMapper.toResource(tipoActividad));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

}
