package com.IngSoftware.proyectosgr.domain.mapping;

import com.IngSoftware.proyectosgr.config.mapping.EnhancedModelMapper;
import com.IngSoftware.proyectosgr.domain.dto.Moneda.CreateMonedaResource;
import com.IngSoftware.proyectosgr.domain.dto.Moneda.MonedaResource;
import com.IngSoftware.proyectosgr.domain.dto.Moneda.MonedaResourceDescription;
import com.IngSoftware.proyectosgr.domain.dto.Moneda.UpdateMonedaResource;
import com.IngSoftware.proyectosgr.domain.model.Moneda;
import com.IngSoftware.proyectosgr.domain.model.TipoActividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class MonedaMapper {

    @Autowired
    EnhancedModelMapper mapper;

    public MonedaResource toResource(Moneda model) {
        return mapper.map(model, MonedaResource.class);
    }

    public Page<MonedaResource> modelListToPage(List<Moneda> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, MonedaResource.class), pageable, modelList.size());
    }

    public List<MonedaResource> modelListToList(List<Moneda> modelList) {
        return mapper.mapList(modelList, MonedaResource.class);
    }

    public MonedaResourceDescription toDeescripcionResource(TipoActividad model) {
        return mapper.map(model, MonedaResourceDescription.class);
    }

    public List<MonedaResourceDescription> modelListToDescripcionList(List<Moneda> modelList) {
        return mapper.mapList(modelList, MonedaResourceDescription.class);
    }

    public Moneda toModel(CreateMonedaResource resource) {
        return mapper.map(resource, Moneda.class);
    }

    public Moneda toModel(UpdateMonedaResource resource) {
        return mapper.map(resource, Moneda.class);
    }
}
