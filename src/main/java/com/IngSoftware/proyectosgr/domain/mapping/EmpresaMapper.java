package com.IngSoftware.proyectosgr.domain.mapping;

import com.IngSoftware.proyectosgr.config.mapping.EnhancedModelMapper;
import com.IngSoftware.proyectosgr.domain.dto.Empresa.*;
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

    public EmpresaMonedaResource toEmpresaMonedaResource(Empresa model) {
        EmpresaMonedaResource dto = new EmpresaMonedaResource();
        dto.setIdempresa(model.getId());
        dto.setNombreComercial(model.getNombrecomercial());

        if (model.getMoneda() != null) {
            dto.setIdmoneda(model.getMoneda().getId());
            dto.setDescripcion(model.getMoneda().getDescripcion());
        }

        return dto;
    }

    // 🔥 NUEVO: Mapear empresa con país
    public EmpresaPaisResource toEmpresaPaisResource(Empresa model) {
        EmpresaPaisResource dto = new EmpresaPaisResource();
        dto.setIdEmpresa(model.getId());
        dto.setNombreComercial(model.getNombrecomercial());

        // Obtener el primer país de la lista (o país por defecto si no hay)
        if (model.getPaises() != null && !model.getPaises().isEmpty()) {
            dto.setIdPais(model.getPaises().get(0).getId());
            dto.setNombre(model.getPaises().get(0).getNombre());
        } else {
            // País por defecto: Perú (ID 173)
            dto.setIdPais(173);
            dto.setNombre("Perú");
        }

        return dto;
    }


}
