package com.IngSoftware.proyectosgr.service;

import com.IngSoftware.proyectosgr.domain.dto.User.UserInformationResource;
import com.IngSoftware.proyectosgr.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface UsuarioService {
    Optional<Usuario> getUserByEmail(String email);

    UserInformationResource getUserInformation();

    Page<Usuario> searchClientesByNombre(String nombre, Pageable pageable);

    Page<Usuario> searchConsultoresByNombre(String nombre, Pageable pageable);

    // ============================================
    // MÉTODOS PARA CONSULTORES (idTipoUsuario = 1)
    // ============================================

    List<Usuario> getAllConsultores();

    Page<Usuario> getAllConsultores(Pageable pageable);

    List<Usuario> getConsultoresActivos();

    Page<Usuario> getConsultoresActivos(Pageable pageable);

    // ============================================
    // MÉTODOS PARA CLIENTES (idTipoUsuario = 2)
    // ============================================

    List<Usuario> getAllClientes();

    Page<Usuario> getAllClientes(Pageable pageable);

    List<Usuario> getClientesActivos();

    Page<Usuario> getClientesActivos(Pageable pageable);

    // ============================================
// MÉTODOS PARA CLIENTES POR EMPRESA
// ============================================

    List<Usuario> getClientesByEmpresa(Integer idEmpresa);

    Page<Usuario> getClientesByEmpresa(Integer idEmpresa, Pageable pageable);

    List<Usuario> getClientesActivosByEmpresa(Integer idEmpresa);

    Page<Usuario> getClientesActivosByEmpresa(Integer idEmpresa, Pageable pageable);

}
