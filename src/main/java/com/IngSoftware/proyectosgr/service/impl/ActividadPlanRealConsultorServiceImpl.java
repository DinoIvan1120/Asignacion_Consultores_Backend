package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.config.exception.ResourceNotFoundException;
import com.IngSoftware.proyectosgr.domain.model.ActividadesPlanRealConsultor;
import com.IngSoftware.proyectosgr.domain.model.Requerimiento;
import com.IngSoftware.proyectosgr.domain.model.TipoActividad;
import com.IngSoftware.proyectosgr.domain.repository.ActividadPlanRealConsultorRepository;
import com.IngSoftware.proyectosgr.domain.repository.RequerimientoRepository;
import com.IngSoftware.proyectosgr.domain.repository.TipoActividadRepository;
import com.IngSoftware.proyectosgr.service.ActividadPlanRealConsultorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ActividadPlanRealConsultorServiceImpl implements ActividadPlanRealConsultorService {

    private static final Logger logger = LoggerFactory.getLogger(ActividadPlanRealConsultorServiceImpl.class);

    @Autowired
    private ActividadPlanRealConsultorRepository actividadRepository;

    @Autowired
    private TipoActividadRepository tipoActividadRepository;

    @Autowired
    private RequerimientoRepository requerimientoRepository;

    @Override
    public Page<ActividadesPlanRealConsultor> searchByFilters(
            Integer idrequerimiento,
            Integer idusuario,
            Integer idtipoactividad,
            Boolean facturable,
            Date fechaInicio,
            Date fechaFin,
            Pageable pageable) {

        logger.info("Filtrando actividades del requerimiento {} con filtros: " +
                        "idusuario={}, idtipoactividad={}, facturable={}, fechaInicio={}, fechaFin={}",
                idrequerimiento, idusuario, idtipoactividad, facturable, fechaInicio, fechaFin);

        return actividadRepository.findByMultipleFilters(
                idrequerimiento,
                idusuario,
                idtipoactividad,
                facturable,
                fechaInicio,
                fechaFin,
                pageable
        );
    }

    @Override
    public List<ActividadesPlanRealConsultor> getAllByRequerimiento(Integer idrequerimiento) {
        logger.info("Listando todas las actividades del requerimiento: {}", idrequerimiento);
        return actividadRepository.findByIdrequerimiento(idrequerimiento);
    }

    @Override
    public Page<ActividadesPlanRealConsultor> getAllByRequerimiento(Integer idrequerimiento, Pageable pageable) {
        logger.info("Listando actividades con paginación del requerimiento: {} (CON JOIN FETCH)", idrequerimiento);
        return actividadRepository.findByIdrequerimientoWithRelations(idrequerimiento, pageable);
    }

    @Override
    public List<ActividadesPlanRealConsultor> getAllByUsuario(Integer idusuario) {
        logger.info("Listando todas las actividades del usuario: {}", idusuario);
        return actividadRepository.findByIdusuario(idusuario);
    }

    @Override
    public ActividadesPlanRealConsultor getById(Integer id, Integer idrequerimiento) {
        logger.info("Buscando actividad {} del requerimiento: {}", id, idrequerimiento);

        return actividadRepository.findByIdAndIdrequerimiento(id, idrequerimiento)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad", "id -> " + id));
    }

    @Override
    public List<ActividadesPlanRealConsultor> getByTipoActividad(Integer idtipoactividad) {
        logger.info("Listando actividades con tipo de actividad: {}", idtipoactividad);
        return actividadRepository.findByIdtipoactividad(idtipoactividad);
    }

    @Override
    public List<ActividadesPlanRealConsultor> getByFacturable(Integer idrequerimiento, Boolean facturable) {
        logger.info("Listando actividades facturables={} del requerimiento: {}", facturable, idrequerimiento);
        return actividadRepository.findByIdrequerimientoAndFacturable(idrequerimiento, facturable);
    }

    @Override
    public List<ActividadesPlanRealConsultor> getByResponsable(Integer idrequerimiento, Boolean responsable) {
        logger.info("Listando actividades con responsable={} del requerimiento: {}", responsable, idrequerimiento);
        return actividadRepository.findByIdrequerimientoAndResponsable(idrequerimiento, responsable);
    }

    @Override
    @Transactional
    public ActividadesPlanRealConsultor create(ActividadesPlanRealConsultor actividad, Integer idrequerimiento) {
        logger.info("Creando nueva actividad para el requerimiento: {}", idrequerimiento);

        // ============================================================
        // 1. Validar que el requerimiento existe
        // ============================================================
        Requerimiento requerimiento = requerimientoRepository.findById(idrequerimiento)
                .orElseThrow(() -> new ResourceNotFoundException("Requerimiento", "id -> " + idrequerimiento));

        // ============================================================
        // 2. Asignar el requerimiento a la actividad
        // ============================================================
        actividad.setIdrequerimiento(idrequerimiento);

        // ============================================================
        // 3. Obtener datos automáticos del requerimiento
        // ============================================================
        // idmoneda: obtener de la empresa del requerimiento
        if (requerimiento.getEmpresa() != null && requerimiento.getEmpresa().getIdmoneda() != null) {
            actividad.setIdmoneda(requerimiento.getEmpresa().getIdmoneda());
            logger.info("Moneda asignada automáticamente: {}", requerimiento.getEmpresa().getIdmoneda());
        } else {
            actividad.setIdmoneda(1); // Default: Soles
            logger.warn("Requerimiento sin moneda, usando Soles (1) por defecto");
        }

        if(actividad.getIdtipoactividad()!=null){
            TipoActividad tipo = tipoActividadRepository.findById(actividad.getIdtipoactividad())
                    .orElseThrow(() -> new ResourceNotFoundException("Tipo de actividad not fount ->", "id -> " + actividad.getIdtipoactividad()));
            actividad.setDescripcion(tipo.getDescripcion());
            logger.info("Tipo de actividad asignado: {}", actividad.getIdtipoactividad());
        }

        // ============================================================
        // 4. Valores automáticos por defecto
        // ============================================================
        actividad.setIdescalatiempo(1);           // Default: Horas
        actividad.setModo("R");                    // Default: Regular
        actividad.setIdasignacion(0);              // Sin asignación
        actividad.setHorasacumuladas(0.0);         // Sin horas acumuladas inicialmente
        actividad.setResponsable(true);            // Es responsable por defecto
        actividad.setPorcentajeAvance(0.0);        // Sin avance inicial
        actividad.setPreAsignado("0");             // No preasignado
        actividad.setIdCategoria(1);

        // Si no se envió facturable, por defecto es true
        if (actividad.getFacturable() == null) {
            actividad.setFacturable(true);
        }

        // Tiempo extra por defecto
        if (actividad.getTiempoextra() == null) {
            actividad.setTiempoextra(0.0);
        }

        // Es móvil por defecto false
        if (actividad.getEsMovil() == null) {
            actividad.setEsMovil(false);
        }

        // ============================================================
        // 5. Información adicional del requerimiento
        // ============================================================
        if (requerimiento.getEmpresa() != null) {
            actividad.setCliente(requerimiento.getEmpresa().getNombrecomercial());
        }
        actividad.setRegtitulo(requerimiento.getTitulo());

        // fecharegistro se genera automáticamente con @CreationTimestamp

        logger.info("Actividad configurada: idmoneda={}, idescalatiempo={}, modo={}, facturable={}",
                actividad.getIdmoneda(), actividad.getIdescalatiempo(), actividad.getModo(), actividad.getFacturable());

        // ============================================================
        // 6. Guardar
        // ============================================================
        ActividadesPlanRealConsultor nuevaActividad = actividadRepository.save(actividad);
        logger.info("Actividad creada exitosamente con ID: {}", nuevaActividad.getId());

        return nuevaActividad;
    }

    @Override
    public ActividadesPlanRealConsultor update(Integer id, ActividadesPlanRealConsultor actividad, Integer idrequerimiento) {
        logger.info("Actualizando actividad {} del requerimiento: {}", id, idrequerimiento);

        // Validar que la actividad existe y pertenece al requerimiento
        ActividadesPlanRealConsultor existingActividad = actividadRepository
                .findByIdAndIdrequerimiento(id, idrequerimiento)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad", "id -> " + id));

        // Mantener campos automáticos
        actividad.setId(id);
        actividad.setIdrequerimiento(existingActividad.getIdrequerimiento());
        actividad.setFecharegistro(existingActividad.getFecharegistro());
        actividad.setIdmoneda(existingActividad.getIdmoneda());
        actividad.setIdescalatiempo(existingActividad.getIdescalatiempo());
        actividad.setModo(existingActividad.getModo());

        ActividadesPlanRealConsultor updatedActividad = actividadRepository.save(actividad);
        logger.info("Actividad {} actualizada exitosamente", id);

        return updatedActividad;
    }

    @Override
    public ActividadesPlanRealConsultor delete(Integer id, Integer idrequerimiento) {
        logger.info("Eliminando actividad {} del requerimiento: {}", id, idrequerimiento);

        // Validar que la actividad existe y pertenece al requerimiento
        ActividadesPlanRealConsultor actividad = actividadRepository
                .findByIdAndIdrequerimiento(id, idrequerimiento)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad", "id -> " + id));

        actividadRepository.delete(actividad);
        logger.info("Actividad {} eliminada exitosamente", id);

        return actividad;
    }
}
