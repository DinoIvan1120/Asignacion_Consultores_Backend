package com.IngSoftware.proyectosgr.domain.mapping;

import com.IngSoftware.proyectosgr.config.mapping.EnhancedModelMapper;
import com.IngSoftware.proyectosgr.domain.dto.Admin.AdminResource;
import com.IngSoftware.proyectosgr.domain.dto.Admin.CreateAdminResource;
import com.IngSoftware.proyectosgr.domain.dto.Admin.UpdateAdminResource;
import com.IngSoftware.proyectosgr.domain.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class AdminMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public AdminResource toResource(Admin model){
        return mapper.map(model, AdminResource.class);
    }

    public Page<AdminResource> modelListToPage(List<Admin> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, AdminResource.class), pageable, modelList.size());
    }

    public List<AdminResource> modelListToList(List<Admin> modelList) {
        return mapper.mapList(modelList, AdminResource.class);
    }

    public Admin toModel(CreateAdminResource resource) {
        return mapper.map(resource, Admin.class);
    }

    public Admin toModel(UpdateAdminResource resource) {
        return mapper.map(resource, Admin.class);
    }

}