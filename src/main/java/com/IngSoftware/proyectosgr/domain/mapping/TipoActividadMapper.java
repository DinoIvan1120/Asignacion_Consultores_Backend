package com.IngSoftware.proyectosgr.domain.mapping;

import com.IngSoftware.proyectosgr.config.mapping.EnhancedModelMapper;
import com.IngSoftware.proyectosgr.domain.dto.TipoActividad.CreateTipoActividadResource;
import com.IngSoftware.proyectosgr.domain.dto.TipoActividad.TipoActividadDescripcionResource;
import com.IngSoftware.proyectosgr.domain.dto.TipoActividad.TipoActividadResource;
import com.IngSoftware.proyectosgr.domain.dto.TipoActividad.UpdateTipoActividadResource;
import com.IngSoftware.proyectosgr.domain.model.TipoActividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class TipoActividadMapper {

    @Autowired
    EnhancedModelMapper mapper;

    public TipoActividadResource toResource(TipoActividad model) {
        return mapper.map(model, TipoActividadResource.class);
    }

    public Page<TipoActividadResource> modelListToPage(List<TipoActividad> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, TipoActividadResource.class), pageable, modelList.size());
    }

    public List<TipoActividadResource> modelListToList(List<TipoActividad> modelList) {
        return mapper.mapList(modelList, TipoActividadResource.class);
    }

    public TipoActividadDescripcionResource toDeescripcionResource(TipoActividad model) {
        return mapper.map(model, TipoActividadDescripcionResource.class);
    }

    public List<TipoActividadDescripcionResource> modelListToDescripcionList(List<TipoActividad> modelList) {
        return mapper.mapList(modelList, TipoActividadDescripcionResource.class);
    }

    public TipoActividad toModel(CreateTipoActividadResource resource) {
        return mapper.map(resource, TipoActividad.class);
    }

    public TipoActividad toModel(UpdateTipoActividadResource resource) {
        return mapper.map(resource, TipoActividad.class);
    }
}
