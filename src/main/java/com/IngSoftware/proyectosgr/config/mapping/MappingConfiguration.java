package com.IngSoftware.proyectosgr.config.mapping;

import com.IngSoftware.proyectosgr.domain.mapping.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("enhancedModelMapperConfiguration")
public class MappingConfiguration {

    @Bean
    public EnhancedModelMapper modelMapper() {
        return new EnhancedModelMapper();
    }

    @Bean
    public AdminMapper adminMapper(){
        return new AdminMapper();
    }

    @Bean
    public CitizenMapper citizenMapper(){
        return new CitizenMapper();
    }

    @Bean
    public EmpresaMapper empresaMapper(){return new EmpresaMapper();}

    @Bean
    public UserMapper usersMapper(){
        return new UserMapper();
    }

    @Bean
    public RequerimientoMapper requerimientoMapper(){return new RequerimientoMapper();}

    @Bean
    public ActividadPlanRealConsultorMapper actividadPlanRealConsultorMapper(){return new ActividadPlanRealConsultorMapper();}

    @Bean
    public TipoActividadMapper tipoActividadMapper(){return new TipoActividadMapper();}

    @Bean
    public MonedaMapper monedaMapper(){return new MonedaMapper();}

    @Bean
    public SubFrenteMapper subFrenteMapper(){return new SubFrenteMapper();}

    @Bean
    public AsignacionesMapper asignacionesMapper(){return new AsignacionesMapper();}
}
