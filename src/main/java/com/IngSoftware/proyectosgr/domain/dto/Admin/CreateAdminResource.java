package com.IngSoftware.proyectosgr.domain.dto.Admin;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateAdminResource {
    // ===== CAMPOS OBLIGATORIOS =====
    private String nombres;
    private String apepaterno;
    private String correo;
    private String clave;

    // ===== CAMPOS OPCIONALES =====

    // Información personal
    private String apematerno;
    private String alias;
    private String usuario;
    private Date fechanacimiento;
    private String sexo;
    private Integer idestadocivil;

    // Documentos
    private String ruc;
    private Integer idtipodocumento;
    private String tipodocumento;

    // Contacto
    private String telefonomovil;
    private String fijo;
    private String correo_personal;
    private String direccion;

    // Empresa y ubicación
    private Integer idempresa;
    private Integer idpais;
    private Integer id_area;
    private Integer id_ccosto;

    // Información adicional
    private String foto;
    private String url_foto;
    private String codigo;
    private String especialidad;

    // Campos de personal
    private String es_personal;
    private String estado_personal;

    // Otros
    private Boolean flag_ponente;
    private Boolean soporte_ext;
    private String acreedor;
    private String asignacion;
    private Date fechacesecuenta;

    private Integer idtipousuario;
}
