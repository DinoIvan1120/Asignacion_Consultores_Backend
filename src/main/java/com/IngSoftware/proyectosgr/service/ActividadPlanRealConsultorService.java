package com.IngSoftware.proyectosgr.service;

import com.IngSoftware.proyectosgr.domain.model.ActividadesPlanRealConsultor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ActividadPlanRealConsultorService {

    /**
     * Búsqueda con múltiples filtros opcionales
     */
    Page<ActividadesPlanRealConsultor> searchByFilters(
            Integer idrequerimiento,
            Integer idusuario,
            Integer idtipoactividad,
            Boolean facturable,
            Date fechaInicio,
            Date fechaFin,
            Pageable pageable
    );

    /**
     * Obtener todas las actividades de un requerimiento
     */
    List<ActividadesPlanRealConsultor> getAllByRequerimiento(Integer idrequerimiento);

    /**
     * Obtener con paginación las actividades de un requerimiento
     */
    Page<ActividadesPlanRealConsultor> getAllByRequerimiento(Integer idrequerimiento, Pageable pageable);

    /**
     * Obtener actividades por usuario/consultor
     */
    List<ActividadesPlanRealConsultor> getAllByUsuario(Integer idusuario);

    /**
     * Obtener por ID (validando que pertenezca al requerimiento especificado)
     */
    ActividadesPlanRealConsultor getById(Integer id, Integer idrequerimiento);

    /**
     * Obtener por tipo de actividad
     */
    List<ActividadesPlanRealConsultor> getByTipoActividad(Integer idtipoactividad);

    /**
     * Obtener actividades facturables de un requerimiento
     */
    List<ActividadesPlanRealConsultor> getByFacturable(Integer idrequerimiento, Boolean facturable);

    /**
     * Obtener actividades donde el usuario es responsable
     */
    List<ActividadesPlanRealConsultor> getByResponsable(Integer idrequerimiento, Boolean responsable);

    /**
     * Crear actividad (asignando automáticamente valores por defecto)
     */
    ActividadesPlanRealConsultor create(ActividadesPlanRealConsultor actividad, Integer idrequerimiento);

    /**
     * Actualizar actividad (validando permisos)
     */
    ActividadesPlanRealConsultor update(Integer id, ActividadesPlanRealConsultor actividad, Integer idrequerimiento);

    /**
     * Eliminar actividad (validando permisos)
     */
    ActividadesPlanRealConsultor delete(Integer id, Integer idrequerimiento);
}
