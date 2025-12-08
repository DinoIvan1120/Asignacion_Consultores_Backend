package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.config.exception.ResourceNotFoundException;
import com.IngSoftware.proyectosgr.domain.dto.ActividadPlanRealConsultor.ActividadPlanRealConsultorResource;
import com.IngSoftware.proyectosgr.domain.dto.Asignacion.AsignacionResource;
import com.IngSoftware.proyectosgr.domain.dto.Asignacion.UpdateAsignacionResource;
import com.IngSoftware.proyectosgr.domain.dto.Requerimiento.RequerimientoResource;
import com.IngSoftware.proyectosgr.domain.mapping.ActividadPlanRealConsultorMapper;
import com.IngSoftware.proyectosgr.domain.mapping.AsignacionesMapper;
import com.IngSoftware.proyectosgr.domain.mapping.RequerimientoMapper;
import com.IngSoftware.proyectosgr.domain.model.ActividadesPlanRealConsultor;
import com.IngSoftware.proyectosgr.domain.model.Requerimiento;
import com.IngSoftware.proyectosgr.domain.model.TipoActividad;
import com.IngSoftware.proyectosgr.domain.model.Usuario;
import com.IngSoftware.proyectosgr.domain.repository.ActividadPlanRealConsultorRepository;
import com.IngSoftware.proyectosgr.domain.repository.RequerimientoRepository;
import com.IngSoftware.proyectosgr.domain.repository.TipoActividadRepository;
import com.IngSoftware.proyectosgr.domain.repository.UsuarioRepository;
import com.IngSoftware.proyectosgr.service.AsignacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignacionServiceImpl implements AsignacionService {

    private static final Logger logger = LoggerFactory.getLogger(AsignacionServiceImpl.class);

    @Autowired
    private RequerimientoRepository requerimientoRepository;

    @Autowired
    private ActividadPlanRealConsultorRepository actividadRepository;

    @Autowired
    private TipoActividadRepository tipoActividadRepository;

    @Autowired
    private RequerimientoMapper requerimientoMapper;

    @Autowired
    private ActividadPlanRealConsultorMapper actividadMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ✅ NUEVO: Inyectar el mapper
    @Autowired
    private AsignacionesMapper asignacionMapper;

    /**
     * ✅ REFACTORIZADO: Buscar asignaciones con filtros usando el mapper
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AsignacionResource> searchAsignacionesByFilters(
            Date fechaInicio,
            Date fechaFin,
            Integer idUsuario,
            String nombreConsultor,
            Pageable pageable) {

        Integer idCoordinador = getAuthenticatedCoordinadorId();

        logger.info("Filtrando asignaciones del coordinador {} con filtros: " +
                        "fechaInicio={}, fechaFin={}, idUsuario={}, nombreConsultor={}",
                idCoordinador, fechaInicio, fechaFin, idUsuario, nombreConsultor);

        // ============================================================
        // 1. Buscar requerimientos con filtros
        // ============================================================
        Page<Requerimiento> requerimientos = requerimientoRepository.findByMultipleFilters(
                idCoordinador,
                null,              // idEmpresa
                null,              // codRequerimiento
                fechaInicio,
                fechaFin,
                idUsuario,
                null,              // idRequerimiento
                null,              // idEstadoRequerimiento
                nombreConsultor,
                pageable
        );

        logger.info("Se encontraron {} requerimientos filtrados en la página {} de {}",
                requerimientos.getNumberOfElements(),
                requerimientos.getNumber(),
                requerimientos.getTotalPages());

        // ============================================================
        // 2. ✅ USAR EL MAPPER para convertir a AsignacionResource
        // ============================================================
        List<AsignacionResource> asignaciones = asignacionMapper.modelListToList(
                requerimientos.getContent()
        );

        // ============================================================
        // 3. Retornar Page<AsignacionResource>
        // ============================================================
        Page<AsignacionResource> pageAsignaciones = new PageImpl<>(
                asignaciones,
                pageable,
                requerimientos.getTotalElements()
        );

        logger.info("Retornando página de asignaciones filtradas: {} elementos de {} totales",
                pageAsignaciones.getNumberOfElements(),
                pageAsignaciones.getTotalElements());

        return pageAsignaciones;
    }

    /**
     * ✅ NUEVO: Obtener todas las asignaciones SIN paginación (para descarga completa)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AsignacionResource> obtenerTodasAsignacionesSinPaginacion() {
        Integer idCoordinador = getAuthenticatedCoordinadorId();

        logger.info("Obteniendo TODAS las asignaciones del coordinador {} (sin paginación)", idCoordinador);

        // Obtener todos los requerimientos del coordinador
        List<Requerimiento> requerimientos =
                requerimientoRepository.findByIdCoordinadorOrderByIdRequerimientoDesc(idCoordinador);

        logger.info("Se encontraron {} requerimientos totales", requerimientos.size());

        // Convertir a AsignacionResource usando el mapper
        List<AsignacionResource> asignaciones = asignacionMapper.modelListToList(requerimientos);

        logger.info("Retornando {} asignaciones completas", asignaciones.size());

        return asignaciones;
    }

    /**
     * ✅ NUEVO: Buscar asignaciones con filtros SIN paginación (para descarga con filtros)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AsignacionResource> searchAsignacionesByFiltersSinPaginacion(
            Date fechaInicio,
            Date fechaFin,
            Integer idUsuario,
            String nombreConsultor) {

        Integer idCoordinador = getAuthenticatedCoordinadorId();

        logger.info("Filtrando asignaciones del coordinador {} SIN paginación con filtros: " +
                        "fechaInicio={}, fechaFin={}, idUsuario={}, nombreConsultor={}",
                idCoordinador, fechaInicio, fechaFin, idUsuario, nombreConsultor);

        // Buscar todos los requerimientos que cumplan los filtros (sin paginación)
        List<Requerimiento> requerimientos =
                requerimientoRepository.findByMultipleFiltersSinPaginacion(
                        idCoordinador,
                        null,
                        null,
                        fechaInicio,
                        fechaFin,
                        idUsuario,
                        null,
                        null,
                        nombreConsultor
                );

        logger.info("Se encontraron {} requerimientos filtrados", requerimientos.size());

        // Convertir a AsignacionResource usando el mapper
        List<AsignacionResource> asignaciones = asignacionMapper.modelListToList(requerimientos);

        logger.info("Retornando {} asignaciones filtradas", asignaciones.size());

        return asignaciones;
    }

    /**
     * Obtiene el ID del usuario coordinador autenticado
     */
    private Integer getAuthenticatedCoordinadorId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        logger.info("Obteniendo ID del coordinador autenticado: {}", username);

        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "usuario", username));

        return usuario.getIdUsuario();
    }

    /**
     * ✅ NUEVO: Obtener todas las asignaciones del coordinador con paginación
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AsignacionResource> obtenerTodasAsignaciones(Pageable pageable) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();

        logger.info("Obteniendo todas las asignaciones del coordinador {} con paginación", idCoordinador);

        // ============================================================
        // 1. Obtener página de requerimientos del coordinador
        // ============================================================
        Page<Requerimiento> requerimientos =
                requerimientoRepository.findByIdCoordinadorWithRelations(idCoordinador, pageable);

        logger.info("Se encontraron {} requerimientos en la página {} de {}",
                requerimientos.getNumberOfElements(),
                requerimientos.getNumber(),
                requerimientos.getTotalPages());

        // ============================================================
        // 2. Para cada requerimiento, construir su AsignacionResource
        // ============================================================
        List<AsignacionResource> asignaciones = new ArrayList<>();

        for (Requerimiento req : requerimientos.getContent()) {
            // Obtener actividades del requerimiento
            List<ActividadesPlanRealConsultor> actividades =
                    actividadRepository.findByIdrequerimiento(req.getIdRequerimiento());

            // Mapear requerimiento
            RequerimientoResource requerimientoDTO = requerimientoMapper.toResource(req);

            // Mapear actividades
            List<ActividadPlanRealConsultorResource> actividadesDTO = actividades.stream()
                    .map(actividad -> actividadMapper.toResource(actividad))
                    .collect(Collectors.toList());

            // Crear asignación completa
            AsignacionResource asignacion = new AsignacionResource();
            asignacion.setRequerimiento(requerimientoDTO);
            asignacion.setActividadPlanRealConsultor(actividadesDTO);

            asignaciones.add(asignacion);

            logger.debug("Requerimiento {} con {} actividades agregado",
                    req.getIdRequerimiento(), actividadesDTO.size());
        }

        // ============================================================
        // 3. Crear Page<AsignacionResource> con la información de paginación
        // ============================================================
        Page<AsignacionResource> pageAsignaciones = new PageImpl<>(
                asignaciones,
                pageable,
                requerimientos.getTotalElements()
        );

        logger.info("Retornando página de asignaciones: {} elementos de {} totales",
                pageAsignaciones.getNumberOfElements(),
                pageAsignaciones.getTotalElements());

        return pageAsignaciones;
    }



    @Override
    @Transactional(readOnly = true)
    public AsignacionResource obtenerAsignacionCompleta(Integer idRequerimiento) {
        logger.info("Obteniendo asignación completa para requerimiento: {}", idRequerimiento);

        // ============================================================
        // 1. Obtener el requerimiento
        // ============================================================
        Requerimiento requerimiento = requerimientoRepository.findById(idRequerimiento)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Requerimiento", "id -> " + idRequerimiento));

        // ============================================================
        // 2. Obtener todas las actividades del requerimiento
        // ============================================================
        List<ActividadesPlanRealConsultor> actividades =
                actividadRepository.findByIdrequerimiento(idRequerimiento);

        // ============================================================
        // 3. Convertir a DTOs usando los mappers existentes
        // ============================================================
        RequerimientoResource requerimientoDTO = requerimientoMapper.toResource(requerimiento);

        List<ActividadPlanRealConsultorResource> actividadesDTO = actividades.stream()
                .map(actividad -> actividadMapper.toResource(actividad))
                .collect(Collectors.toList());

        // ============================================================
        // 4. Combinar en el DTO completo
        // ============================================================
        AsignacionResource asignacionCompleta = new AsignacionResource();
        asignacionCompleta.setRequerimiento(requerimientoDTO);
        asignacionCompleta.setActividadPlanRealConsultor(actividadesDTO);

        logger.info("Asignación completa obtenida: Requerimiento {} con {} actividades",
                idRequerimiento, actividadesDTO.size());

        return asignacionCompleta;
    }

    @Override
    @Transactional
    public AsignacionResource actualizarAsignacionCompleta(
            Integer idRequerimiento,
            UpdateAsignacionResource actualizacion) {

        logger.info("Actualizando asignación completa para requerimiento: {}", idRequerimiento);

        // ============================================================
        // 1. Validar que el requerimiento existe
        // ============================================================
        Requerimiento requerimiento = requerimientoRepository.findById(idRequerimiento)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Requerimiento", "id -> " + idRequerimiento));

        // ============================================================
        // 2. Actualizar datos del requerimiento (si se enviaron)
        // ============================================================
        if (actualizacion.getRequerimiento() != null) {
            actualizarRequerimiento(requerimiento, actualizacion.getRequerimiento());
            requerimientoRepository.save(requerimiento);
            logger.info("Requerimiento {} actualizado exitosamente", idRequerimiento);
        }

        // ============================================================
        // 3. Actualizar actividades (si se enviaron)
        // ============================================================
        if (actualizacion.getActividades() != null && !actualizacion.getActividades().isEmpty()) {

            for (UpdateAsignacionResource.ActividadUpdateDTO actividadUpdate :
                    actualizacion.getActividades()) {

                if (actividadUpdate.getId() != null) {
                    // Buscar la actividad
                    ActividadesPlanRealConsultor actividad = actividadRepository
                            .findById(actividadUpdate.getId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Actividad", "id -> " + actividadUpdate.getId()));

                    // Validar que la actividad pertenece al requerimiento
                    if (!actividad.getIdrequerimiento().equals(idRequerimiento)) {
                        throw new IllegalArgumentException(
                                "La actividad " + actividadUpdate.getId() +
                                        " no pertenece al requerimiento " + idRequerimiento);
                    }

                    // Actualizar la actividad
                    actualizarActividad(actividad, actividadUpdate);
                    actividadRepository.save(actividad);

                    logger.info("Actividad {} actualizada exitosamente", actividadUpdate.getId());
                }
            }
        }

        // ============================================================
        // 4. Retornar la asignación actualizada
        // ============================================================
        logger.info("Asignación completa actualizada exitosamente");
        return obtenerAsignacionCompleta(idRequerimiento);
    }

    // ========== Métodos auxiliares de actualización ==========

    /**
     * Actualiza los campos del requerimiento que vienen en el DTO
     */
    private void actualizarRequerimiento(
            Requerimiento req,
            UpdateAsignacionResource.RequerimientoUpdateDTO update) {

        if (update.getTitulo() != null) {
            req.setTitulo(update.getTitulo());
        }
        if (update.getDetalle() != null) {
            req.setDetalle(update.getDetalle());
        }
        if (update.getIdSubfrente() != null) {
            req.setIdSubfrente(update.getIdSubfrente());
        }
        if (update.getIdUsuario() != null) {
            req.setIdUsuario(update.getIdUsuario());
        }
        if (update.getDescripcionEstimacion() != null) {
            req.setDescripcionEstimacion(update.getDescripcionEstimacion());
        }
        if (update.getDetalleAsignacion() != null) {
            req.setDetalleAsignacion(update.getDetalleAsignacion());
        }
        if (update.getOrdenCompra() != null) {
            req.setOrdenCompra(update.getOrdenCompra());
        }
    }

    /**
     * Actualiza los campos de la actividad que vienen en el DTO
     */
    private void actualizarActividad(
            ActividadesPlanRealConsultor act,
            UpdateAsignacionResource.ActividadUpdateDTO update) {

        if (update.getIdusuario() != null) {
            act.setIdusuario(update.getIdusuario());
        }
        if (update.getFechainicio() != null) {
            act.setFechainicio(update.getFechainicio());
        }
        if (update.getFechafin() != null) {
            act.setFechafin(update.getFechafin());
        }
        if (update.getIdtipoactividad() != null) {
            act.setIdtipoactividad(update.getIdtipoactividad());

            // Actualizar descripción según el tipo de actividad
            TipoActividad tipo = tipoActividadRepository.findById(update.getIdtipoactividad())
                    .orElse(null);
            if (tipo != null) {
                act.setDescripcion(tipo.getDescripcion());
            }
        }
        if (update.getTiemporegular() != null) {
            act.setTiemporegular(update.getTiemporegular());
        }
        if (update.getCosto() != null) {
            act.setCosto(update.getCosto());
        }
        if (update.getFacturable() != null) {
            act.setFacturable(update.getFacturable());
        }
        if (update.getTiempoextra() != null) {
            act.setTiempoextra(update.getTiempoextra());
        }
        if (update.getPorcentajeAvance() != null) {
            act.setPorcentajeAvance(update.getPorcentajeAvance());
        }
        if (update.getDescripcion() != null) {
            act.setDescripcion(update.getDescripcion());
        }
    }



}
