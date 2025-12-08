package com.IngSoftware.proyectosgr.domain.mapping;

import com.IngSoftware.proyectosgr.config.mapping.EnhancedModelMapper;
import com.IngSoftware.proyectosgr.domain.dto.User.UserInformationResource;
import com.IngSoftware.proyectosgr.domain.dto.User.UserResource;
import com.IngSoftware.proyectosgr.domain.dto.Usuario.UsuarioSimpleResource;
import com.IngSoftware.proyectosgr.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class UserMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;
    public UserResource toResource(Usuario model){
        return mapper.map(model, UserResource.class);
    }

    public Page<UserResource> modelListToPage(List<Usuario> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, UserResource.class), pageable, modelList.size());
    }

    public List<UserResource> modelListToList(List<Usuario> modelList) {
        return mapper.mapList(modelList, UserResource.class);
    }

    /**
     * Mapea un Usuario a UserInformationResource con lógica personalizada
     * @param usuario Entidad Usuario
     * @return UserInformationResource con información completa del usuario
     */
    public UserInformationResource toInformationResource(Usuario usuario) {
        // Obtener rol del usuario desde idTipoUsuario
        String roleName = obtenerNombreRol(usuario);

        // Construir nombre completo
        String nombreCompleto = construirNombreCompleto(usuario);

        // Obtener nombre de empresa (si tiene relación)
        String nombreEmpresa = null;
        if (usuario.getEmpresa() != null) {
            nombreEmpresa = usuario.getEmpresa().getNombrecomercial();
        }

        // Crear y retornar el resource
        UserInformationResource resource = new UserInformationResource();
        resource.setIdUsuario(usuario.getIdUsuario());
        resource.setUsuario(usuario.getUsuario());
        resource.setCorreo(usuario.getCorreo());
        resource.setNombres(usuario.getNombres());
        resource.setApepaterno(usuario.getApepaterno());
        resource.setApematerno(usuario.getApematerno());
        resource.setNombreCompleto(nombreCompleto);
        resource.setRol(roleName);
        resource.setEstado(usuario.getEstado());
        resource.setTelefonomovil(usuario.getTelefonomovil());
        resource.setIdempresa(usuario.getIdempresa());
        resource.setNombreEmpresa(nombreEmpresa);
        resource.setFecharegistro(usuario.getFecharegistro());

        return resource;
    }

    /**
     * Obtiene el nombre del rol desde idTipoUsuario
     * @param usuario Usuario
     * @return Nombre del rol
     */
    private String obtenerNombreRol(Usuario usuario) {
        // OPCIÓN 1: Usar la relación con TipoUsuario (RECOMENDADO)
        // Si TipoUsuario tiene un campo nombre/descripcion
        if (usuario.getTipoUsuario() != null) {
            // Ajusta según el campo que tenga tu entidad TipoUsuario
            return usuario.getTipoUsuario().getDescripcion(); // O .getDescripcion() según tu modelo
        }
        // Valor por defecto
        return "Cliente";
    }

    /**
     * Construye el nombre completo del usuario
     * @param usuario Usuario
     * @return Nombre completo
     */
    private String construirNombreCompleto(Usuario usuario) {
        String nombreCompleto = "";

        if (usuario.getNombres() != null) {
            nombreCompleto = usuario.getNombres();
        }
        if (usuario.getApepaterno() != null) {
            nombreCompleto += (nombreCompleto.isEmpty() ? "" : " ") + usuario.getApepaterno();
        }
        if (usuario.getApematerno() != null) {
            nombreCompleto += (nombreCompleto.isEmpty() ? "" : " ") + usuario.getApematerno();
        }

        // Si no hay nombres completos, usar el usuario
        if (nombreCompleto.trim().isEmpty()) {
            nombreCompleto = usuario.getUsuario();
        }

        return nombreCompleto;
    }

    /**
     * Mapea una lista de Usuarios a lista de UserInformationResource
     * @param modelList Lista de Usuarios
     * @return Lista de UserInformationResource
     */
    public List<UserInformationResource> modelListToInformationList(List<Usuario> modelList) {
        return modelList.stream()
                .map(this::toInformationResource)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Mapea una lista de Usuarios a Page de UserInformationResource
     * @param modelList Lista de Usuarios
     * @param pageable Información de paginación
     * @return Page de UserInformationResource
     */
    public Page<UserInformationResource> modelListToInformationPage(List<Usuario> modelList, Pageable pageable) {
        List<UserInformationResource> resourceList = modelListToInformationList(modelList);
        return new PageImpl<>(resourceList, pageable, modelList.size());
    }

    /**
     * Convierte Usuario a UsuarioSimpleResource para dropdowns
     */
    public UsuarioSimpleResource toSimpleResource(Usuario model) {
        UsuarioSimpleResource resource = mapper.map(model, UsuarioSimpleResource.class);

        // Reutilizar el método construirNombreCompleto que ya existe
        resource.setNombreCompleto(construirNombreCompleto(model));

        // Agregar tipo de usuario si está disponible
        if (model.getTipoUsuario() != null) {
            resource.setDescripcionTipoUsuario(model.getTipoUsuario().getDescripcion());
        }

        return resource;
    }

    /**
     * Convierte lista de Usuario a lista de UsuarioSimpleResource
     */
    public List<UsuarioSimpleResource> modelListToSimpleList(List<Usuario> modelList) {
        return modelList.stream()
                .map(this::toSimpleResource)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Convierte lista de Usuario a Page de UsuarioSimpleResource
     */
    public Page<UsuarioSimpleResource> modelListToSimplePage(List<Usuario> modelList, Pageable pageable) {
        List<UsuarioSimpleResource> resourceList = modelListToSimpleList(modelList);
        return new PageImpl<>(resourceList, pageable, modelList.size());
    }
}