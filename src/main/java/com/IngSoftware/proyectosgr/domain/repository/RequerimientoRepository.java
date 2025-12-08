package com.IngSoftware.proyectosgr.domain.repository;

import com.IngSoftware.proyectosgr.domain.model.Requerimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface    RequerimientoRepository extends JpaRepository<Requerimiento, Integer> {

    /**
     * ✅ NUEVO: Buscar todos los requerimientos del coordinador sin paginación
     * (para descarga completa)
     */
    @Query("SELECT r FROM Requerimiento r " +
            "LEFT JOIN FETCH r.estadoRequerimiento " +
            "LEFT JOIN FETCH r.prioridad " +
            "LEFT JOIN FETCH r.empresa e " +
            "LEFT JOIN FETCH e.moneda " +
            "LEFT JOIN FETCH r.usuario " +
            "WHERE r.idCoordinador = :idCoordinador " +
            "ORDER BY r.idRequerimiento DESC")
    List<Requerimiento> findByIdCoordinadorOrderByIdRequerimientoDesc(
            @Param("idCoordinador") Integer idCoordinador
    );

    /**
     * ✅ NUEVO: Buscar con filtros múltiples SIN paginación
     * (para descarga con filtros)
     */
    @Query(
            value = "SELECT DISTINCT r FROM Requerimiento r " +
                    "LEFT JOIN FETCH r.estadoRequerimiento " +
                    "LEFT JOIN FETCH r.prioridad " +
                    "LEFT JOIN FETCH r.empresa e " +
                    "LEFT JOIN FETCH e.moneda " +
                    "LEFT JOIN FETCH r.usuario " +
                    "WHERE r.idCoordinador = :idCoordinador " +

                    // ====== FILTROS CON COALESCE ======
                    "AND r.idEmpresa = COALESCE(:idEmpresa, r.idEmpresa) " +
                    "AND (COALESCE(:codRequerimiento, '') = '' OR LOWER(r.codRequerimiento) LIKE LOWER(CONCAT('%', :codRequerimiento, '%'))) " +
                    "AND r.fechaEnvio >= COALESCE(:fechaInicio, r.fechaEnvio) " +
                    "AND r.fechaEnvio <= COALESCE(:fechaFin, r.fechaEnvio) " +
                    "AND r.idUsuario = COALESCE(:idUsuario, r.idUsuario) " +
                    "AND r.idRequerimiento = COALESCE(:idRequerimiento, r.idRequerimiento) " +
                    "AND r.idEstadoRequerimiento = COALESCE(:idEstadoRequerimiento, r.idEstadoRequerimiento) " +
                    "AND (COALESCE(:nombreConsultor, '') = '' OR LOWER(r.titulo) LIKE LOWER(CONCAT('%', :nombreConsultor, '%'))) " +

                    "ORDER BY r.idRequerimiento DESC"
    )
    List<Requerimiento> findByMultipleFiltersSinPaginacion(
            @Param("idCoordinador") Integer idCoordinador,
            @Param("idEmpresa") Integer idEmpresa,
            @Param("codRequerimiento") String codRequerimiento,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin,
            @Param("idUsuario") Integer idUsuario,
            @Param("idRequerimiento") Integer idRequerimiento,
            @Param("idEstadoRequerimiento") Integer idEstadoRequerimiento,
            @Param("nombreConsultor") String nombreConsultor
    );
    //Cambio

    @Query("SELECT r.codRequerimiento FROM Requerimiento r " +
            "WHERE r.idEmpresa = :idEmpresa AND YEAR(r.fechaRegistro) = :anio " +
            "ORDER BY r.codRequerimiento DESC")
    List<String> findCodigosByEmpresaAndYear(@Param("idEmpresa") Integer idEmpresa,
                                             @Param("anio") Integer anio);


       @Query(
            // ========== QUERY PRINCIPAL ==========
            value = "SELECT DISTINCT r FROM Requerimiento r " +
                    "LEFT JOIN FETCH r.estadoRequerimiento " +
                    "LEFT JOIN FETCH r.prioridad " +
                    "LEFT JOIN FETCH r.empresa e " +
                    "LEFT JOIN FETCH e.moneda " +
                    "LEFT JOIN FETCH r.usuario " +
                    "WHERE r.idCoordinador = :idCoordinador " +
                    "AND r.idEmpresa = COALESCE(:idEmpresa, r.idEmpresa) " +
                    "AND (COALESCE(:codRequerimiento, '') = '' OR LOWER(r.codRequerimiento) LIKE LOWER(CONCAT('%', :codRequerimiento, '%'))) " +
                    "AND r.fechaRegistro >= COALESCE(:fechaInicio, r.fechaRegistro) " +
                    "AND r.fechaRegistro <= COALESCE(:fechaFin, r.fechaRegistro) " +
                    "AND r.idUsuario = COALESCE(:idUsuario, r.idUsuario) " +
                    // En QUERY PRINCIPAL, agregar ANTES de ORDER BY:
                    "AND (COALESCE(:nombreConsultor, '') = '' OR LOWER(r.titulo) LIKE LOWER(CONCAT('%', :nombreConsultor, '%'))) " +
                    "AND r.idRequerimiento = COALESCE(:idRequerimiento, r.idRequerimiento) " +
                    "AND r.idEstadoRequerimiento = COALESCE(:idEstadoRequerimiento, r.idEstadoRequerimiento) " +
                    "ORDER BY r.idRequerimiento DESC",

            // ========== COUNT QUERY ==========
            countQuery = "SELECT COUNT(r) FROM Requerimiento r " +
                    "WHERE r.idCoordinador = :idCoordinador " +
                    "AND r.idEmpresa = COALESCE(:idEmpresa, r.idEmpresa) " +
                    "AND (COALESCE(:codRequerimiento, '') = '' OR LOWER(r.codRequerimiento) LIKE LOWER(CONCAT('%', :codRequerimiento, '%'))) " +
                    "AND r.fechaRegistro >= COALESCE(:fechaInicio, r.fechaRegistro) " +
                    "AND r.fechaRegistro <= COALESCE(:fechaFin, r.fechaRegistro) " +
                    "AND r.idUsuario = COALESCE(:idUsuario, r.idUsuario) " +
                    "AND r.idRequerimiento = COALESCE(:idRequerimiento, r.idRequerimiento) " +
                    // En COUNT QUERY, agregar AL FINAL:
                    "AND (COALESCE(:nombreConsultor, '') = '' OR LOWER(r.titulo) LIKE LOWER(CONCAT('%', :nombreConsultor, '%')))" +
                    "AND r.idEstadoRequerimiento = COALESCE(:idEstadoRequerimiento, r.idEstadoRequerimiento)"
    )
    Page<Requerimiento> findByMultipleFilters(
            @Param("idCoordinador") Integer idCoordinador,
            @Param("idEmpresa") Integer idEmpresa,
            @Param("codRequerimiento") String codRequerimiento,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin,
            @Param("idUsuario") Integer idUsuario,
            @Param("idRequerimiento") Integer idRequerimiento,
            @Param("idEstadoRequerimiento") Integer idEstadoRequerimiento,
            // En parámetros del método:
            @Param("nombreConsultor") String nombreConsultor,
            Pageable pageable
    );

    // Listar todos los requerimientos de un coordinador específico
    List<Requerimiento> findByIdCoordinador(Integer idCoordinador);

    @Query(
            value = "SELECT r FROM Requerimiento r " +
                    "LEFT JOIN FETCH r.estadoRequerimiento " +
                    "LEFT JOIN FETCH r.prioridad " +
                    "LEFT JOIN FETCH r.empresa " +
                    "LEFT JOIN FETCH r.usuario " +
                    "WHERE r.idCoordinador = :idCoordinador " +
                    "ORDER BY r.idRequerimiento DESC",

            countQuery = "SELECT COUNT(r) FROM Requerimiento r " +
                    "WHERE r.idCoordinador = :idCoordinador"
    )
    Page<Requerimiento> findByIdCoordinadorWithRelations(
            @Param("idCoordinador") Integer idCoordinador,
            Pageable pageable
    );

    @Query(
            value = "SELECT r FROM Requerimiento r " +
                    "WHERE r.idCoordinador = :idCoordinador " +
                    "ORDER BY r.idRequerimiento DESC",
            countQuery = "SELECT COUNT(r) FROM Requerimiento r WHERE r.idCoordinador = :idCoordinador"
    )
    Page<Requerimiento> findCodigosByIdCoordinador(
            @Param("idCoordinador") Integer idCoordinador,
            Pageable pageable
    );

    // Listar con paginación los requerimientos de un coordinador
    Page<Requerimiento> findByIdCoordinador(Integer idCoordinador, Pageable pageable);

    // Buscar por ID y coordinador (para validar permisos)
    Optional<Requerimiento> findByIdRequerimientoAndIdCoordinador(Integer idRequerimiento, Integer idCoordinador);

    // Buscar por código de requerimiento y coordinador
    Optional<Requerimiento> findByCodRequerimientoAndIdCoordinador(String codRequerimiento, Integer idCoordinador);

    // Listar por estado de requerimiento y coordinador
    List<Requerimiento> findByIdEstadoRequerimientoAndIdCoordinador(Integer idEstadoRequerimiento, Integer idCoordinador);

    // Listar por empresa y coordinador
    List<Requerimiento> findByIdEmpresaAndIdCoordinador(Integer idEmpresa, Integer idCoordinador);

    // Listar por prioridad y coordinador
    List<Requerimiento> findByIdPrioridadAndIdCoordinador(Integer idPrioridad, Integer idCoordinador);

    // Listar por urgente y coordinador
    List<Requerimiento> findByUrgenteAndIdCoordinador(Boolean urgente, Integer idCoordinador);

    // Listar por subfrente y coordinador
    List<Requerimiento> findByIdSubfrenteAndIdCoordinador(Integer idSubfrente, Integer idCoordinador);
}
