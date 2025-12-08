package com.IngSoftware.proyectosgr.domain.mapping;

import com.IngSoftware.proyectosgr.config.mapping.EnhancedModelMapper;
import com.IngSoftware.proyectosgr.domain.dto.Empresa.CreateEmpresaResource;
import com.IngSoftware.proyectosgr.domain.dto.Empresa.EmpresaNombreComercialResource;
import com.IngSoftware.proyectosgr.domain.dto.Empresa.EmpresaResource;
import com.IngSoftware.proyectosgr.domain.dto.Empresa.UpdateEmpresaResource;
import com.IngSoftware.proyectosgr.domain.model.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class EmpresaMapper {

    @Autowired
    EnhancedModelMapper mapper;

    public EmpresaResource toResource(Empresa model) {
        return mapper.map(model, EmpresaResource.class);
    }

    public Page<EmpresaResource> modelListToPage(List<Empresa> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, EmpresaResource.class), pageable, modelList.size());
    }

    public List<EmpresaResource> modelListToList(List<Empresa> modelList) {
        return mapper.mapList(modelList, EmpresaResource.class);
    }

    public EmpresaNombreComercialResource toNombreComercialResource(Empresa model) {
        return mapper.map(model, EmpresaNombreComercialResource.class);
    }

    public List<EmpresaNombreComercialResource> modelListToNombreComercialList(List<Empresa> modelList) {
        return mapper.mapList(modelList, EmpresaNombreComercialResource.class);
    }

    public Empresa toModel(CreateEmpresaResource resource) {
        return mapper.map(resource, Empresa.class);
    }

    public Empresa toModel(UpdateEmpresaResource resource) {
        return mapper.map(resource, Empresa.class);
    }
}
