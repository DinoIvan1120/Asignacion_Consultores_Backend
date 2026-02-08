package com.IngSoftware.proyectosgr.domain.repository;

import com.IngSoftware.proyectosgr.domain.model.ActividadesPlanRealConsultor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ActividadPlanRealConsultorRepository extends JpaRepository<ActividadesPlanRealConsultor, Integer> {


    /**
     * Listar todas las actividades de un requerimiento específico
     */
    List<ActividadesPlanRealConsultor> findByIdrequerimiento(Integer idrequerimiento);


    @Query("SELECT a FROM ActividadesPlanRealConsultor a " +
            "WHERE a.idrequerimiento = :idRequerimiento " +
            "AND a.idusuario IS NOT NULL " +   // ← AGREGAR
            "AND a.idusuario != 0 " +          // ← AGREGAR
            "ORDER BY a.fechainicio DESC")
    List<ActividadesPlanRealConsultor> findByIdrequerimientoExcludeZero(@Param("idRequerimiento") Integer idrequerimiento);

    /**
     * Listar con paginación las actividades de un requerimiento
     */
    Page<ActividadesPlanRealConsultor> findByIdrequerimiento(Integer idrequerimiento, Pageable pageable);

    /**
     * Listar actividades de un requerimiento con JOIN FETCH (para traer relaciones)
     */
    @Query(
            value = "SELECT a FROM ActividadesPlanRealConsultor a " +
                    "LEFT JOIN FETCH a.moneda " +
                    "LEFT JOIN FETCH a.tipoActividad " +
                    "LEFT JOIN FETCH a.escalaTiempo " +
                    "LEFT JOIN FETCH a.requerimiento " +
                    "LEFT JOIN FETCH a.usuario " +
                    "LEFT JOIN FETCH a.categoriaActividad " +
                    "WHERE a.idrequerimiento = :idrequerimiento " +
                    "ORDER BY a.fechainicio DESC",
            countQuery = "SELECT COUNT(a) FROM ActividadesPlanRealConsultor a " +
                    "WHERE a.idrequerimiento = :idrequerimiento"
    )
    Page<ActividadesPlanRealConsultor> findByIdrequerimientoWithRelations(
            @Param("idrequerimiento") Integer idrequerimiento,
            Pageable pageable
    );

    // ============================================================
    // Consultas por usuario/consultor
    // ============================================================

    /**
     * Listar actividades de un usuario/consultor
     */
    List<ActividadesPlanRealConsultor> findByIdusuario(Integer idusuario);

    /**
     * Listar actividades de un usuario con paginación
     */
    Page<ActividadesPlanRealConsultor> findByIdusuario(Integer idusuario, Pageable pageable);

    // ============================================================
    // Búsqueda por ID con validación de requerimiento
    // ============================================================

    /**
     * Buscar actividad por ID y requerimiento (para validar permisos)
     */
    Optional<ActividadesPlanRealConsultor> findByIdAndIdrequerimiento(Integer id, Integer idrequerimiento);

    // ============================================================
    // Consultas por tipo de actividad
    // ============================================================

    /**
     * Listar actividades por tipo de actividad
     */
    List<ActividadesPlanRealConsultor> findByIdtipoactividad(Integer idtipoactividad);

    // ============================================================
    // Consultas por facturable
    // ============================================================

    /**
     * Listar actividades facturables de un requerimiento
     */
    List<ActividadesPlanRealConsultor> findByIdrequerimientoAndFacturable(
            Integer idrequerimiento,
            Boolean facturable
    );

    // ============================================================
    // Consultas por responsable
    // ============================================================

    /**
     * Listar actividades donde el usuario es responsable
     */
    List<ActividadesPlanRealConsultor> findByIdrequerimientoAndResponsable(
            Integer idrequerimiento,
            Boolean responsable
    );

    // ============================================================
    // Búsqueda con filtros múltiples
    // ============================================================

    @Query(
            value = "SELECT DISTINCT a FROM ActividadesPlanRealConsultor a " +
                    "LEFT JOIN FETCH a.moneda " +
                    "LEFT JOIN FETCH a.tipoActividad " +
                    "LEFT JOIN FETCH a.escalaTiempo " +
                    "LEFT JOIN FETCH a.requerimiento " +
                    "LEFT JOIN FETCH a.usuario " +
                    "LEFT JOIN FETCH a.categoriaActividad " +
                    "WHERE a.idrequerimiento = :idrequerimiento " +
                    "AND a.idusuario = COALESCE(:idusuario, a.idusuario) " +
                    "AND a.idtipoactividad = COALESCE(:idtipoactividad, a.idtipoactividad) " +
                    "AND a.facturable = COALESCE(:facturable, a.facturable) " +
                    "AND a.fechainicio >= COALESCE(:fechaInicio, a.fechainicio) " +
                    "AND a.fechafin <= COALESCE(:fechaFin, a.fechafin) " +
                    "ORDER BY a.fechainicio DESC",
            countQuery = "SELECT COUNT(a) FROM ActividadesPlanRealConsultor a " +
                    "WHERE a.idrequerimiento = :idrequerimiento " +
                    "AND a.idusuario = COALESCE(:idusuario, a.idusuario) " +
                    "AND a.idtipoactividad = COALESCE(:idtipoactividad, a.idtipoactividad) " +
                    "AND a.facturable = COALESCE(:facturable, a.facturable) " +
                    "AND a.fechainicio >= COALESCE(:fechaInicio, a.fechainicio) " +
                    "AND a.fechafin <= COALESCE(:fechaFin, a.fechafin)"
    )
    Page<ActividadesPlanRealConsultor> findByMultipleFilters(
            @Param("idrequerimiento") Integer idrequerimiento,
            @Param("idusuario") Integer idusuario,
            @Param("idtipoactividad") Integer idtipoactividad,
            @Param("facturable") Boolean facturable,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin,
            Pageable pageable
    );
}
