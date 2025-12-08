package com.IngSoftware.proyectosgr.domain.mapping;

import com.IngSoftware.proyectosgr.config.mapping.EnhancedModelMapper;
import com.IngSoftware.proyectosgr.domain.dto.ActividadPlanRealConsultor.ActividadPlanRealConsultorResource;
import com.IngSoftware.proyectosgr.domain.dto.Asignacion.AsignacionResource;
import com.IngSoftware.proyectosgr.domain.dto.Requerimiento.RequerimientoResource;
import com.IngSoftware.proyectosgr.domain.model.ActividadesPlanRealConsultor;
import com.IngSoftware.proyectosgr.domain.model.Requerimiento;
import com.IngSoftware.proyectosgr.domain.repository.ActividadPlanRealConsultorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class AsignacionesMapper {

    @Autowired
    private EnhancedModelMapper mapper;

    @Autowired
    private RequerimientoMapper requerimientoMapper;

    @Autowired
    private ActividadPlanRealConsultorMapper actividadMapper;

    @Autowired
    private ActividadPlanRealConsultorRepository actividadRepository;

    /**
     * Convierte un Requerimiento (entidad) a AsignacionResource (DTO completo)
     * Incluye el requerimiento Y todas sus actividades asociadas
     *
     * @param requerimiento Entidad Requerimiento
     * @return AsignacionResource con requerimiento + actividades
     */
    public AsignacionResource toResource(Requerimiento requerimiento) {
        // ============================================================
        // 1. Mapear el requerimiento a DTO
        // ============================================================
        RequerimientoResource requerimientoDTO = requerimientoMapper.toResource(requerimiento);

        // ============================================================
        // 2. Obtener las actividades del requerimiento
        // ============================================================
        List<ActividadesPlanRealConsultor> actividades =
                actividadRepository.findByIdrequerimiento(requerimiento.getIdRequerimiento());

        // ============================================================
        // 3. Mapear las actividades a DTOs
        // ============================================================
        List<ActividadPlanRealConsultorResource> actividadesDTO = actividades.stream()
                .map(actividad -> actividadMapper.toResource(actividad))
                .collect(Collectors.toList());

        // ============================================================
        // 4. Crear la asignación completa
        // ============================================================
        AsignacionResource asignacion = new AsignacionResource();
        asignacion.setRequerimiento(requerimientoDTO);
        asignacion.setActividadPlanRealConsultor(actividadesDTO);

        return asignacion;
    }

    /**
     * Convierte una lista de Requerimientos a lista de AsignacionResource
     * Útil para conversiones masivas manteniendo el orden
     *
     * @param requerimientos Lista de entidades Requerimiento
     * @return Lista de AsignacionResource
     */
    public List<AsignacionResource> modelListToList(List<Requerimiento> requerimientos) {
        return requerimientos.stream()
                .map(this::toResource)
                .collect(Collectors.toList());
    }

    /**
     * Versión optimizada: Convierte una lista de Requerimientos a AsignacionResource
     * con sus actividades ya precargadas (evita N+1 queries si ya tienes las actividades)
     *
     * @param requerimiento Entidad Requerimiento
     * @param actividades Actividades ya cargadas del requerimiento
     * @return AsignacionResource
     */
    public AsignacionResource toResourceWithActivities(
            Requerimiento requerimiento,
            List<ActividadesPlanRealConsultor> actividades) {

        // Mapear requerimiento
        RequerimientoResource requerimientoDTO = requerimientoMapper.toResource(requerimiento);

        // Mapear actividades (ya las tenemos, no hacemos query)
        List<ActividadPlanRealConsultorResource> actividadesDTO = actividades.stream()
                .map(actividad -> actividadMapper.toResource(actividad))
                .collect(Collectors.toList());

        // Crear asignación completa
        AsignacionResource asignacion = new AsignacionResource();
        asignacion.setRequerimiento(requerimientoDTO);
        asignacion.setActividadPlanRealConsultor(actividadesDTO);

        return asignacion;
    }
}
