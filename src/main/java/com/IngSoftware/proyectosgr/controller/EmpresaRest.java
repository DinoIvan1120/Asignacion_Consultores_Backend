package com.IngSoftware.proyectosgr.controller;

import com.IngSoftware.proyectosgr.domain.dto.Empresa.CreateEmpresaResource;
import com.IngSoftware.proyectosgr.domain.dto.Empresa.EmpresaNombreComercialResource;
import com.IngSoftware.proyectosgr.domain.dto.Empresa.EmpresaResource;
import com.IngSoftware.proyectosgr.domain.dto.Empresa.UpdateEmpresaResource;
import com.IngSoftware.proyectosgr.domain.mapping.EmpresaMapper;
import com.IngSoftware.proyectosgr.domain.model.Empresa;
import com.IngSoftware.proyectosgr.service.EmpresaService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaRest {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private EmpresaMapper mapper;

    @Operation(summary = "Obtener todas las empresas", description = "Retorna una lista con todas las empresas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de empresas obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaResource.class)))
    })
    @GetMapping
    public ResponseEntity<List<EmpresaResource>> getAllEmpresas() {
        List<Empresa> empresas = empresaService.getAll();
        List<EmpresaResource> resources = mapper.modelListToList(empresas);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener todas las empresas con paginación", description = "Retorna una página con las empresas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de empresas obtenida exitosamente")
    })
    @GetMapping("/page")
    public ResponseEntity<Page<EmpresaResource>> getAllEmpresasPage(Pageable pageable) {
        Page<Empresa> empresas = empresaService.getAll(pageable);
        Page<EmpresaResource> resources = mapper.modelListToPage(empresas.getContent(), pageable);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping(path = "/nombrecomercial", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<EmpresaNombreComercialResource>> getNombresComerciales() {
        List<Empresa> empresas = empresaService.getNombresComerciales();
        List<EmpresaNombreComercialResource> resources = mapper.modelListToNombreComercialList(empresas);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener empresa por ID", description = "Retorna una empresa según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResource> getEmpresaById(@PathVariable Integer id) {
        Empresa empresa = empresaService.getById(id);
        EmpresaResource resource = mapper.toResource(empresa);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @Operation(summary = "Obtener empresas por estado", description = "Retorna empresas filtradas por estado activo/inactivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de empresas por estado")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EmpresaResource>> getEmpresasByEstado(@PathVariable Boolean estado) {
        List<Empresa> empresas = empresaService.getByEstado(estado);
        List<EmpresaResource> resources = mapper.modelListToList(empresas);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener empresa por RUC", description = "Retorna una empresa según su RUC")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    @GetMapping("/ruc/{ruc}")
    public ResponseEntity<EmpresaResource> getEmpresaByRuc(@PathVariable String ruc) {
        Empresa empresa = empresaService.getByRuc(ruc);
        EmpresaResource resource = mapper.toResource(empresa);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @Operation(summary = "Obtener empresa por ID SAP", description = "Retorna una empresa según su ID SAP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    @GetMapping("/sap/{idempresaSap}")
    public ResponseEntity<EmpresaResource> getEmpresaByIdSap(@PathVariable String idempresaSap) {
        Empresa empresa = empresaService.getByIdempresaSap(idempresaSap);
        EmpresaResource resource = mapper.toResource(empresa);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @Operation(summary = "Obtener empresas por empresa padre", description = "Retorna empresas hijas de una empresa padre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de empresas hijas")
    })
    @GetMapping("/padre/{empresaPadre}")
    public ResponseEntity<List<EmpresaResource>> getEmpresasByPadre(@PathVariable Integer empresaPadre) {
        List<Empresa> empresas = empresaService.getByEmpresaPadre(empresaPadre);
        List<EmpresaResource> resources = mapper.modelListToList(empresas);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Crear nueva empresa", description = "Crea una nueva empresa en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empresa creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<EmpresaResource> createEmpresa(@Valid @RequestBody CreateEmpresaResource resource) {
        Empresa empresa = mapper.toModel(resource);
        Empresa newEmpresa = empresaService.create(empresa);
        EmpresaResource empresaResource = mapper.toResource(newEmpresa);
        return new ResponseEntity<>(empresaResource, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar empresa", description = "Actualiza los datos de una empresa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResource> updateEmpresa(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateEmpresaResource resource) {
        Empresa empresa = mapper.toModel(resource);
        Empresa updatedEmpresa = empresaService.update(id, empresa);
        EmpresaResource empresaResource = mapper.toResource(updatedEmpresa);
        return new ResponseEntity<>(empresaResource, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar empresa", description = "Elimina una empresa del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<EmpresaResource> deleteEmpresa(@PathVariable Integer id) {
        Empresa empresa = empresaService.delete(id);
        EmpresaResource resource = mapper.toResource(empresa);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}


