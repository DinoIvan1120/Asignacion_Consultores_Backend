package com.IngSoftware.proyectosgr.service;

import com.IngSoftware.proyectosgr.domain.model.Requerimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface RequerimientoService {

    /**
     * ✅ NUEVO: Búsqueda con múltiples filtros opcionales
     *
     * @param idEmpresa ID de la empresa (opcional)
     * @param codRequerimiento Código del requerimiento (opcional, búsqueda parcial)
     * @param fechaInicio Fecha de inicio del rango (opcional)
     * @param fechaFin Fecha de fin del rango (opcional)
     * @param idUsuario ID del usuario/cliente (opcional)
     * @param idRequerimiento ID del requerimiento (opcional, para búsqueda por número de ticket)
     * @param idEstadoRequerimiento ID del estado (opcional)
     * @param pageable Información de paginación
     * @return Página de requerimientos filtrados
     */
    Page<Requerimiento> searchByFilters(
            Integer idEmpresa,
            String codRequerimiento,
            Date fechaInicio,
            Date fechaFin,
            Integer idUsuario,
            Integer idRequerimiento,
            Integer idEstadoRequerimiento,
            // En la firma del método, agregar:
            String nombreConsultor,  // ANTES de Pageable
            Pageable pageable
    );

    Page<Requerimiento> searchByFiltersIdCodigoRequerimiento(Pageable pageable);

    Page<Requerimiento> getCodigosByCoordinador(Pageable pageable);

    // Obtener todos los requerimientos del coordinador autenticado
    List<Requerimiento> getAllByCoordinador();

    // Obtener con paginación los requerimientos del coordinador autenticado
    Page<Requerimiento> getAllByCoordinador(Pageable pageable);

    // Obtener por ID (validando que pertenezca al coordinador autenticado)
    Requerimiento getById(Integer id);

    // Obtener por código de requerimiento
    Requerimiento getByCodRequerimiento(String codRequerimiento);

    // Obtener por estado de requerimiento
    List<Requerimiento> getByEstadoRequerimiento(Integer idEstadoRequerimiento);

    // Obtener por empresa
    List<Requerimiento> getByEmpresa(Integer idEmpresa);

    // Obtener por prioridad
    List<Requerimiento> getByPrioridad(Integer idPrioridad);

    // Obtener por urgente
    List<Requerimiento> getByUrgente(Boolean urgente);

    // Obtener por subfrente
    List<Requerimiento> getBySubfrente(Integer idSubfrente);

    // Crear requerimiento (asignando automáticamente el coordinador)
    Requerimiento create(Requerimiento requerimiento);

    // Actualizar requerimiento (validando permisos)
    Requerimiento update(Integer id, Requerimiento requerimiento);

    // Eliminar requerimiento (validando permisos)
    Requerimiento delete(Integer id);
}
