package com.IngSoftware.proyectosgr.service;

import com.IngSoftware.proyectosgr.domain.dto.Asignacion.AsignacionResource;
import com.IngSoftware.proyectosgr.domain.dto.Asignacion.UpdateAsignacionResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface AsignacionService {

    /**
     * Obtener requerimiento completo con todas sus actividades
     *
     * @param idRequerimiento ID del requerimiento
     * @return Asignación completa con requerimiento y actividades
     */
    AsignacionResource obtenerAsignacionCompleta(Integer idRequerimiento);

    /**
     * Actualizar requerimiento y sus actividades en una sola transacción
     *
     * @param idRequerimiento ID del requerimiento
     * @param actualizacion Datos a actualizar
     * @return Asignación completa actualizada
     */
    AsignacionResource actualizarAsignacionCompleta(
            Integer idRequerimiento,
            UpdateAsignacionResource actualizacion
    );

    /**
     * ✅ NUEVO: Obtener todas las asignaciones del coordinador autenticado con paginación
     *
     * @param pageable Información de paginación
     * @return Página de asignaciones completas (requerimiento + actividades)
     */
    Page<AsignacionResource> obtenerTodasAsignaciones(Pageable pageable);

    /**
     * ✅ NUEVO: Buscar asignaciones con filtros múltiples
     *
     * @param fechaInicio Fecha inicio del requerimiento
     * @param fechaFin Fecha fin del requerimiento
     * @param idUsuario ID del cliente (usuario del requerimiento)
     * @param nombreConsultor Nombre del consultor (busca en el título del requerimiento)
     * @param pageable Información de paginación
     * @return Página de asignaciones filtradas
     */
    Page<AsignacionResource> searchAsignacionesByFilters(
            Date fechaInicio,
            Date fechaFin,
            Integer idUsuario,
            String nombreConsultor,
            Integer idEmpresa,
            String codRequerimiento,
            Integer idRequerimiento,
            Integer idEstadoRequerimiento,
            Pageable pageable
    );

    /**
     * ✅ NUEVO: Obtener todas las asignaciones SIN paginación (para descarga completa)
     *
     * @return Lista completa de asignaciones del coordinador
     */
    List<AsignacionResource> obtenerTodasAsignacionesSinPaginacion();

    /**
     * ✅ NUEVO: Buscar asignaciones con filtros SIN paginación (para descarga con filtros)
     *
     * @param fechaInicio Fecha inicio del requerimiento
     * @param fechaFin Fecha fin del requerimiento
     * @param idUsuario ID del cliente
     * @param nombreConsultor Nombre del consultor
     * @return Lista de asignaciones filtradas
     */
    List<AsignacionResource> searchAsignacionesByFiltersSinPaginacion(
            Date fechaInicio,
            Date fechaFin,
            Integer idUsuario,
            String nombreConsultor

    );
}
