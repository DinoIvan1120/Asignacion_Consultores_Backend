package com.IngSoftware.proyectosgr.domain.dto.Empresa;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateEmpresaResource {

    @NotNull
    private Integer id;

    @NotNull
    private Integer idmoneda;

    @NotNull
    private Integer idfrente;

    @NotBlank
    @Size(max = 80)
    private String razonsocial;

    @Size(max = 80)
    private String nombrecomercial;

    @NotBlank
    @Size(max = 20)
    private String ruc;

    @Size(max = 80)
    private String direccion;

    @Size(max = 20)
    private String telefono;

    private Boolean estado;
    private Boolean codinterno;

    @Size(max = 250)
    private String logo;

    @Size(max = 3)
    private String prefijonombrecomercial;

    private Boolean usuarioSolicitante;
    private Boolean activarPais;
    private Integer tiempoPrueba;
    private Boolean aplicaDetraccion;
    private Integer empresaPadre;
    private Boolean prioridad;
    private Boolean aplicaCierreAutomatico;

    @Size(max = 10)
    private String idempresaSap;

    @Size(max = 4)
    private String idBukrs;
}
