package com.IngSoftware.proyectosgr.domain.mapping;

import com.IngSoftware.proyectosgr.config.mapping.EnhancedModelMapper;
import com.IngSoftware.proyectosgr.domain.dto.EstadoRequerimiento.CreateEstadoRequerimientoResource;
import com.IngSoftware.proyectosgr.domain.dto.EstadoRequerimiento.EstadoRequerimientoResource;
import com.IngSoftware.proyectosgr.domain.dto.EstadoRequerimiento.UpdateEstadoRequerimientoResource;
import com.IngSoftware.proyectosgr.domain.model.EstadoRequerimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoRequerimientoMapper {

    @Autowired
    private EnhancedModelMapper mapper;

    // Model to Resource
    public EstadoRequerimientoResource toResource(EstadoRequerimiento model) {
        return mapper.map(model, EstadoRequerimientoResource.class);
    }

    // Resource to Model
    public EstadoRequerimiento toModel(CreateEstadoRequerimientoResource resource) {
        return mapper.map(resource, EstadoRequerimiento.class);
    }

    public EstadoRequerimiento toModel(UpdateEstadoRequerimientoResource resource) {
        return mapper.map(resource, EstadoRequerimiento.class);
    }

    // List to Resource List
    public List<EstadoRequerimientoResource> modelListToList(List<EstadoRequerimiento> modelList) {
        return modelList.stream()
                .map(this::toResource)
                .collect(Collectors.toList());
    }

    // Page to Resource Page
    public Page<EstadoRequerimientoResource> modelListToPage(List<EstadoRequerimiento> modelList, Pageable pageable) {
        List<EstadoRequerimientoResource> resourceList = modelListToList(modelList);
        return new PageImpl<>(resourceList, pageable, resourceList.size());
    }
}
