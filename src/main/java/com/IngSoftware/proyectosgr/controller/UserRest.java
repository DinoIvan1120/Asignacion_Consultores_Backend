package com.IngSoftware.proyectosgr.controller;

import com.IngSoftware.proyectosgr.domain.dto.Admin.AdminResource;
import com.IngSoftware.proyectosgr.domain.dto.Admin.CreateAdminResource;
import com.IngSoftware.proyectosgr.domain.dto.User.UserInformationResource;
import com.IngSoftware.proyectosgr.domain.dto.Usuario.UsuarioSimpleResource;
import com.IngSoftware.proyectosgr.domain.mapping.AdminMapper;
import com.IngSoftware.proyectosgr.domain.mapping.UserMapper;
import com.IngSoftware.proyectosgr.domain.model.Usuario;
import com.IngSoftware.proyectosgr.service.AdminService;
import com.IngSoftware.proyectosgr.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserRest {
    @Autowired
    private UsuarioService usersService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private AdminMapper adminMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @PostMapping(path = "/save-admin", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AdminResource> saveAdmin(@RequestBody CreateAdminResource resource) throws Exception {
        AdminResource admin = adminMapper.toResource(this.adminService.save(adminMapper.toModel(resource)));
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @GetMapping(path = "/information",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserInformationResource> getInformation() {

        UserInformationResource info = usersService.getUserInformation();
        return new ResponseEntity<>(info, HttpStatus.OK);
    }



    // ============================================
    // ENDPOINTS PARA CONSULTORES (idTipoUsuario = 1)
    // ============================================

    @Operation(summary = "Obtener todos los consultores",
            description = "Retorna una lista con todos los usuarios de tipo Consultor (idTipoUsuario = 1)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consultores obtenida exitosamente")
    })
    @GetMapping("/consultores")
    public ResponseEntity<List<UsuarioSimpleResource>> getAllConsultores() {
        List<Usuario> consultores = usersService.getAllConsultores();
        List<UsuarioSimpleResource> resources = mapper.modelListToSimpleList(consultores);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener todos los consultores con paginación",
            description = "Retorna una página con todos los usuarios de tipo Consultor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de consultores obtenida exitosamente")
    })
    @GetMapping("/consultores/page")
    public ResponseEntity<Page<UsuarioSimpleResource>> getAllConsultoresPage(Pageable pageable) {
        Page<Usuario> consultores = usersService.getAllConsultores(pageable);
        Page<UsuarioSimpleResource> resources = mapper.modelListToSimplePage(consultores.getContent(), pageable);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener consultores activos",
            description = "Retorna una lista con todos los consultores activos (estado = true)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consultores activos obtenida exitosamente")
    })
    @GetMapping("/consultores/activos")
    public ResponseEntity<List<UsuarioSimpleResource>> getConsultoresActivos() {
        List<Usuario> consultores = usersService.getConsultoresActivos();
        List<UsuarioSimpleResource> resources = mapper.modelListToSimpleList(consultores);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener consultores activos con paginación",
            description = "Retorna una página con todos los consultores activos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de consultores activos obtenida exitosamente")
    })
    @GetMapping("/consultores/activos/page")
    public ResponseEntity<Page<UsuarioSimpleResource>> getConsultoresActivosPage(Pageable pageable) {
        Page<Usuario> consultores = usersService.getConsultoresActivos(pageable);
        Page<UsuarioSimpleResource> resources = consultores.map(mapper::toSimpleResource);
        return ResponseEntity.ok(resources);
        //Page<UsuarioSimpleResource> resources = mapper.modelListToSimplePage(consultores.getContent(), pageable);
        //return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    // ============================================
    // ENDPOINTS PARA CLIENTES (idTipoUsuario = 2)
    // ============================================

    @Operation(summary = "Obtener todos los clientes",
            description = "Retorna una lista con todos los usuarios de tipo Cliente (idTipoUsuario = 2)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente")
    })
    @GetMapping("/clientes")
    public ResponseEntity<List<UsuarioSimpleResource>> getAllClientes() {
        List<Usuario> clientes = usersService.getAllClientes();
        List<UsuarioSimpleResource> resources = mapper.modelListToSimpleList(clientes);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener todos los clientes con paginación",
            description = "Retorna una página con todos los usuarios de tipo Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de clientes obtenida exitosamente")
    })
    @GetMapping("/clientes/page")
    public ResponseEntity<Page<UsuarioSimpleResource>> getAllClientesPage(Pageable pageable) {
        Page<Usuario> clientes = usersService.getAllClientes(pageable);
        Page<UsuarioSimpleResource> resources = mapper.modelListToSimplePage(clientes.getContent(), pageable);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener clientes activos",
            description = "Retorna una lista con todos los clientes activos (estado = true)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes activos obtenida exitosamente")
    })
    @GetMapping("/clientes/activos")
    public ResponseEntity<List<UsuarioSimpleResource>> getClientesActivos() {
        List<Usuario> clientes = usersService.getClientesActivos();
        List<UsuarioSimpleResource> resources = mapper.modelListToSimpleList(clientes);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener clientes activos con paginación",
            description = "Retorna una página con todos los clientes activos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de clientes activos obtenida exitosamente")
    })
    @GetMapping("/clientes/activos/page")
    public ResponseEntity<Page<UsuarioSimpleResource>> getClientesActivosPage(Pageable pageable) {

        Page<Usuario> clientes = usersService.getClientesActivos(pageable);

        Page<UsuarioSimpleResource> resources =
                clientes.map(mapper::toSimpleResource);

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/clientes/search")
    public ResponseEntity<Page<UsuarioSimpleResource>> searchClientes(
            @RequestParam String nombre,
            Pageable pageable) {

        Page<Usuario> clientes = usersService.searchClientesByNombre(nombre, pageable);
        Page<UsuarioSimpleResource> resources =
                mapper.modelListToSimplePage(clientes.getContent(), pageable);

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/consultores/search")
    public ResponseEntity<Page<UsuarioSimpleResource>> searchConsultores(
            @RequestParam String nombre,
            Pageable pageable) {

        Page<Usuario> consultores = usersService.searchConsultoresByNombre(nombre, pageable);
        Page<UsuarioSimpleResource> resources =
                mapper.modelListToSimplePage(consultores.getContent(), pageable);

        return ResponseEntity.ok(resources);
    }

    // ============================================
// ENDPOINTS PARA CLIENTES POR EMPRESA
// ============================================

    @Operation(summary = "Obtener clientes por empresa",
            description = "Retorna todos los clientes (idTipoUsuario = 2) de una empresa específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes de la empresa obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    @GetMapping("/clientes/empresa/{idEmpresa}")
    public ResponseEntity<List<UsuarioSimpleResource>> getClientesByEmpresa(@PathVariable Integer idEmpresa) {
        List<Usuario> clientes = usersService.getClientesByEmpresa(idEmpresa);
        List<UsuarioSimpleResource> resources = mapper.modelListToSimpleList(clientes);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener clientes por empresa con paginación",
            description = "Retorna una página con todos los clientes de una empresa específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de clientes de la empresa obtenida exitosamente")
    })
    @GetMapping("/clientes/empresa/{idEmpresa}/page")
    public ResponseEntity<Page<UsuarioSimpleResource>> getClientesByEmpresaPage(
            @PathVariable Integer idEmpresa,
            Pageable pageable) {
        Page<Usuario> clientes = usersService.getClientesByEmpresa(idEmpresa, pageable);
        Page<UsuarioSimpleResource> resources = mapper.modelListToSimplePage(clientes.getContent(), pageable);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener clientes activos por empresa",
            description = "Retorna todos los clientes activos (estado = true) de una empresa específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes activos de la empresa obtenida exitosamente")
    })
    @GetMapping("/clientes/empresa/{idEmpresa}/activos")
    public ResponseEntity<List<UsuarioSimpleResource>> getClientesActivosByEmpresa(@PathVariable Integer idEmpresa) {
        List<Usuario> clientes = usersService.getClientesActivosByEmpresa(idEmpresa);
        List<UsuarioSimpleResource> resources = mapper.modelListToSimpleList(clientes);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Obtener clientes activos por empresa con paginación",
            description = "Retorna una página con todos los clientes activos de una empresa específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de clientes activos de la empresa obtenida exitosamente")
    })
    @GetMapping("/clientes/empresa/{idEmpresa}/activos/page")
    public ResponseEntity<Page<UsuarioSimpleResource>> getClientesActivosByEmpresaPage(
            @PathVariable Integer idEmpresa,
            Pageable pageable) {
        Page<Usuario> clientes = usersService.getClientesActivosByEmpresa(idEmpresa, pageable);
        Page<UsuarioSimpleResource> resources = mapper.modelListToSimplePage(clientes.getContent(), pageable);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

}
