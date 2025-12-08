package com.IngSoftware.proyectosgr.domain.mapping;

import com.IngSoftware.proyectosgr.config.mapping.EnhancedModelMapper;
import com.IngSoftware.proyectosgr.domain.dto.Citizen.CitizenResource;
import com.IngSoftware.proyectosgr.domain.dto.Empresa.EmpresaResource;
import com.IngSoftware.proyectosgr.domain.dto.EstadoRequerimiento.EstadoRequerimientoResource;
import com.IngSoftware.proyectosgr.domain.dto.Prioridad.PrioridadResource;
import com.IngSoftware.proyectosgr.domain.dto.Requerimiento.CreateRequerimientoResource;
import com.IngSoftware.proyectosgr.domain.dto.Requerimiento.RequerimientoResource;
import com.IngSoftware.proyectosgr.domain.dto.Requerimiento.UpdateRequerimientoResource;
import com.IngSoftware.proyectosgr.domain.model.Requerimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class RequerimientoMapper {

    @Autowired
    EnhancedModelMapper mapper;


    /**
     * Convierte un Requerimiento (entidad) a RequerimientoResource (DTO)
     * Mapea automáticamente los campos básicos Y manualmente las relaciones
     */
    public RequerimientoResource toResource(Requerimiento model) {
        // Mapeo automático de campos básicos
        RequerimientoResource resource = mapper.map(model, RequerimientoResource.class);

        // ===================================================
        // ✅ MAPEO MANUAL DE OBJETOS RELACIONADOS
        // ===================================================

        // Estado del Requerimiento
        if (model.getEstadoRequerimiento() != null) {
            EstadoRequerimientoResource estadoDTO = mapper.map(model.getEstadoRequerimiento(), EstadoRequerimientoResource.class);
            resource.setEstadoRequerimiento(estadoDTO);
        }

        // Prioridad
        if (model.getPrioridad() != null) {
            PrioridadResource prioridadDTO = mapper.map(model.getPrioridad(), PrioridadResource.class);
            resource.setPrioridad(prioridadDTO);
        }

        // Empresa
        if (model.getEmpresa() != null) {
            EmpresaResource empresaDTO = mapper.map(model.getEmpresa(), EmpresaResource.class);
            resource.setEmpresa(empresaDTO);
        }

        // Usuario/Consultor
        if (model.getUsuario() != null) {
            CitizenResource usuarioDTO = mapper.map(model.getUsuario(), CitizenResource.class);
            resource.setUsuario(usuarioDTO);
        }

        return resource;
    }


    public Page<RequerimientoResource> modelListToPage(List<Requerimiento> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, RequerimientoResource.class), pageable, modelList.size());
    }

    public List<RequerimientoResource> modelListToList(List<Requerimiento> modelList) {
        return mapper.mapList(modelList, RequerimientoResource.class);
    }

    public Requerimiento toModel(CreateRequerimientoResource resource) {
        Requerimiento requerimiento = new Requerimiento();

        // ✅ NO mapeamos idRequerimiento (es autogenerado por la BD)
        requerimiento.setTitulo(resource.getTitulo());
        requerimiento.setIdEmpresa(resource.getIdEmpresa());
        requerimiento.setIdSubfrente(resource.getIdSubfrente());
        requerimiento.setIdUsuario(resource.getIdUsuario());
        requerimiento.setDetalle(resource.getDetalle());
        requerimiento.setDescripcionEstimacion(resource.getDescripcionEstimacion());
        requerimiento.setDetalleAsignacion(resource.getDetalleAsignacion());
        requerimiento.setOrdenCompra(resource.getOrdenCompra());

        return requerimiento;
    }

    /**
     * Convierte UpdateRequerimientoResource (DTO) a Requerimiento (entidad)
     * Mapeo MANUAL para evitar conflictos con idRequerimiento
     */
    public Requerimiento toModel(UpdateRequerimientoResource resource) {
        Requerimiento requerimiento = new Requerimiento();

        // ✅ NO mapeamos idRequerimiento (se maneja en el servicio)
        requerimiento.setTitulo(resource.getTitulo());
        requerimiento.setIdEmpresa(resource.getIdEmpresa());
        requerimiento.setIdSubfrente(resource.getIdSubfrente());
        requerimiento.setIdUsuario(resource.getIdUsuario());
        requerimiento.setDetalle(resource.getDetalle());
        requerimiento.setDescripcionEstimacion(resource.getDescripcionEstimacion());
        requerimiento.setDetalleAsignacion(resource.getDetalleAsignacion());
        requerimiento.setOrdenCompra(resource.getOrdenCompra());

        return requerimiento;
    }


}
