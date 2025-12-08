package com.IngSoftware.proyectosgr.domain.mapping;

import com.IngSoftware.proyectosgr.config.mapping.EnhancedModelMapper;
import com.IngSoftware.proyectosgr.domain.dto.ActividadPlanRealConsultor.ActividadPlanRealConsultorResource;
import com.IngSoftware.proyectosgr.domain.dto.ActividadPlanRealConsultor.CreateActividadPlanRealConsultorResource;
import com.IngSoftware.proyectosgr.domain.dto.ActividadPlanRealConsultor.UpdateActividadPlanRealConsultorResource;
import com.IngSoftware.proyectosgr.domain.dto.CategoriaActividad.CategoriaActividadResource;
import com.IngSoftware.proyectosgr.domain.dto.Citizen.CitizenResource;
import com.IngSoftware.proyectosgr.domain.dto.EscalaTiempo.EscalaTiempoResource;
import com.IngSoftware.proyectosgr.domain.dto.Moneda.MonedaResource;
import com.IngSoftware.proyectosgr.domain.dto.Requerimiento.RequerimientoResource;
import com.IngSoftware.proyectosgr.domain.dto.TipoActividad.TipoActividadResource;
import com.IngSoftware.proyectosgr.domain.model.ActividadesPlanRealConsultor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ActividadPlanRealConsultorMapper {

    @Autowired
    EnhancedModelMapper mapper;

    /**
     * Convierte ActividadesPlanRealConsultor (entidad) a ActividadPlanRealConsultorResource (DTO)
     * Mapea automáticamente los campos básicos Y manualmente las relaciones
     */
    public ActividadPlanRealConsultorResource toResource(ActividadesPlanRealConsultor model) {
        // Mapeo automático de campos básicos
        ActividadPlanRealConsultorResource resource = mapper.map(model, ActividadPlanRealConsultorResource.class);

        // ===================================================
        // ✅ MAPEO MANUAL DE OBJETOS RELACIONADOS
        // ===================================================

        // Moneda
        if (model.getMoneda() != null) {
            MonedaResource monedaDTO = mapper.map(model.getMoneda(), MonedaResource.class);
            resource.setMoneda(monedaDTO);
        }

        // Tipo de Actividad
        if (model.getTipoActividad() != null) {
            TipoActividadResource tipoActividadDTO = mapper.map(model.getTipoActividad(), TipoActividadResource.class);
            resource.setTipoActividad(tipoActividadDTO);
        }

        // Escala de Tiempo
        if (model.getEscalaTiempo() != null) {
            EscalaTiempoResource escalaTiempoDTO = mapper.map(model.getEscalaTiempo(), EscalaTiempoResource.class);
            resource.setEscalaTiempo(escalaTiempoDTO);
        }

        // Requerimiento
        if (model.getRequerimiento() != null) {
            RequerimientoResource requerimientoDTO = mapper.map(model.getRequerimiento(), RequerimientoResource.class);
            resource.setRequerimiento(requerimientoDTO);
        }

        // Categoría de Actividad
        if (model.getCategoriaActividad() != null) {
            CategoriaActividadResource categoriaDTO = mapper.map(model.getCategoriaActividad(), CategoriaActividadResource.class);
            resource.setCategoriaActividad(categoriaDTO);
        }

        // Usuario/Consultor
        if (model.getUsuario() != null) {
            CitizenResource usuarioDTO = mapper.map(model.getUsuario(), CitizenResource.class);
            resource.setUsuario(usuarioDTO);
        }

        return resource;
    }

    public Page<ActividadPlanRealConsultorResource> modelListToPage(List<ActividadesPlanRealConsultor> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, ActividadPlanRealConsultorResource.class), pageable, modelList.size());
    }

    public List<ActividadPlanRealConsultorResource> modelListToList(List<ActividadesPlanRealConsultor> modelList) {
        return mapper.mapList(modelList, ActividadPlanRealConsultorResource.class);
    }

    public ActividadesPlanRealConsultor toModel(CreateActividadPlanRealConsultorResource resource) {
        return mapper.map(resource, ActividadesPlanRealConsultor.class);
    }

    public ActividadesPlanRealConsultor toModel(UpdateActividadPlanRealConsultorResource resource) {
        return mapper.map(resource, ActividadesPlanRealConsultor.class);
    }
}
