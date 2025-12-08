package com.IngSoftware.proyectosgr.domain.dto.Empresa;

import com.IngSoftware.proyectosgr.domain.dto.Moneda.MonedaResource;
import com.IngSoftware.proyectosgr.domain.model.Moneda;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmpresaResource {
    private Integer id;
    private Integer idmoneda;
    private Integer idfrente;
    private String razonsocial;
    private String nombrecomercial;
    private String ruc;
    private String direccion;
    private String telefono;
    private Date fecharegistro;
    private Date fechamodificacion;
    private Boolean estado;
    private Boolean codinterno;
    private String logo;
    private String prefijonombrecomercial;
    private Boolean usuarioSolicitante;
    private Boolean activarPais;
    private Integer tiempoPrueba;
    private Boolean aplicaDetraccion;
    private Integer empresaPadre;
    private Boolean prioridad;
    private Boolean aplicaCierreAutomatico;
    private String idempresaSap;
    private String idBukrs;

    private MonedaResource moneda;
}
