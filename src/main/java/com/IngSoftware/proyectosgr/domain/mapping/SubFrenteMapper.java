package com.IngSoftware.proyectosgr.domain.mapping;

import com.IngSoftware.proyectosgr.config.mapping.EnhancedModelMapper;
import com.IngSoftware.proyectosgr.domain.dto.Frente.FrenteResource;
import com.IngSoftware.proyectosgr.domain.dto.SubFrente.CreateSubFrenteResource;
import com.IngSoftware.proyectosgr.domain.dto.SubFrente.SubFrenteDescripcionResource;
import com.IngSoftware.proyectosgr.domain.dto.SubFrente.SubFrenteResource;
import com.IngSoftware.proyectosgr.domain.dto.SubFrente.UpdateSubFrenteResource;
import com.IngSoftware.proyectosgr.domain.model.SubFrente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class SubFrenteMapper {

    @Autowired
    EnhancedModelMapper mapper;

    public SubFrenteResource toResource(SubFrente model) {
        SubFrenteResource subFrenteResource =  mapper.map(model, SubFrenteResource.class);

        //Mapeo manual de objetos relacionados
        if(model.getFrente() != null) {
            FrenteResource frenteDTO = mapper.map(model.getFrente(), FrenteResource.class);
            subFrenteResource.setFrente(frenteDTO);
        }
        return subFrenteResource;
    }

    public Page<SubFrenteResource> modelListToPage(List<SubFrente> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, SubFrenteResource.class), pageable, modelList.size());
    }

    public List<SubFrenteResource> modelListToList(List<SubFrente> modelList) {
        return mapper.mapList(modelList, SubFrenteResource.class);
    }

    public SubFrenteDescripcionResource toDeescripcionResource(SubFrente model) {
        return mapper.map(model, SubFrenteDescripcionResource.class);
    }

    public List<SubFrenteDescripcionResource> modelListToDescripcionList(List<SubFrente> modelList) {
        return mapper.mapList(modelList, SubFrenteDescripcionResource.class);
    }

    public SubFrente toModel(CreateSubFrenteResource resource) {
        return mapper.map(resource, SubFrente.class);
    }

    public SubFrente toModel(UpdateSubFrenteResource resource) {
        return mapper.map(resource, SubFrente.class);
    }
}
