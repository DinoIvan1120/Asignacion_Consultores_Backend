package com.IngSoftware.proyectosgr.domain.dto.Asignacion;

import com.IngSoftware.proyectosgr.domain.dto.ActividadPlanRealConsultor.ActividadPlanRealConsultorResource;
import com.IngSoftware.proyectosgr.domain.dto.Requerimiento.RequerimientoResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AsignacionResource {

    private RequerimientoResource requerimiento;

    private List<ActividadPlanRealConsultorResource> actividadPlanRealConsultor;
}
