package com.IngSoftware.proyectosgr.domain.repository;

import com.IngSoftware.proyectosgr.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE u.correo = ?1")
    Optional<Usuario> getUserByEmail(String email);
    @Query("SELECT u.idUsuario FROM Usuario  u WHERE u.correo = ?1")
    Integer getUserIdByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.idTipoUsuario = 2 AND LOWER(CONCAT(u.nombres, ' ', u.apepaterno, ' ', u.apematerno)) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Usuario> searchClientesByNombres(
            @Param("nombre") String nombre,
            Pageable pageable);

    @Query("SELECT u FROM Usuario u WHERE u.idTipoUsuario = 1 AND LOWER(CONCAT(u.nombres, ' ', u.apepaterno, ' ', u.apematerno)) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Usuario> searchConsultoresByNombres(
            @Param("nombre") String nombre,
            Pageable pageable);

    // 🔥 Nuevo método requerido por el servicio
    Optional<Usuario> findByUsuario(String usuario);

    // ============================================
    // QUERIES PARA CONSULTORES (idTipoUsuario = 1)
    // ============================================

    @Query("SELECT u FROM Usuario u WHERE u.idTipoUsuario = 1")
    List<Usuario> findAllConsultores();

    @Query("SELECT u FROM Usuario u WHERE u.idTipoUsuario = 1")
    Page<Usuario> findAllConsultores(Pageable pageable);

    @Query("SELECT u FROM Usuario u WHERE u.idTipoUsuario = 1 AND u.estado = true")
    List<Usuario> findConsultoresActivos();

    @Query("SELECT u FROM Usuario u WHERE u.idTipoUsuario = 1 AND u.estado = true")
    Page<Usuario> findConsultoresActivos(Pageable pageable);

    // ============================================
    // QUERIES PARA CLIENTES (idTipoUsuario = 2)
    // ============================================

    @Query("SELECT u FROM Usuario u WHERE u.idTipoUsuario = 2")
    List<Usuario> findAllClientes();

    @Query("SELECT u FROM Usuario u WHERE u.idTipoUsuario = 2")
    Page<Usuario> findAllClientes(Pageable pageable);

    //@Query("SELECT u FROM Usuario u WHERE u.idTipoUsuario = 2 AND u.estado = true")
    @Query("SELECT u FROM Usuario u WHERE u.idTipoUsuario = 2 AND u.estado = true")
    List<Usuario> findClientesActivos();

    @Query("SELECT u FROM Usuario u WHERE u.idTipoUsuario = 2 AND u.estado = true")
    Page<Usuario> findClientesActivos(Pageable pageable);

    // QUERIES PARA CLIENTES POR EMPRESA
// ============================================

    @Query("SELECT u FROM Usuario u WHERE u.idempresa = :idEmpresa AND u.idTipoUsuario = 2")
    List<Usuario> findClientesByEmpresa(@Param("idEmpresa") Integer idEmpresa);

    @Query("SELECT u FROM Usuario u WHERE u.idempresa = :idEmpresa AND u.idTipoUsuario = 2")
    Page<Usuario> findClientesByEmpresa(@Param("idEmpresa") Integer idEmpresa, Pageable pageable);

    @Query("SELECT u FROM Usuario u WHERE u.idempresa = :idEmpresa AND u.idTipoUsuario = 2 AND u.estado = true")
    List<Usuario> findClientesActivosByEmpresa(@Param("idEmpresa") Integer idEmpresa);

    @Query("SELECT u FROM Usuario u WHERE u.idempresa = :idEmpresa AND u.idTipoUsuario = 2 AND u.estado = true")
    Page<Usuario> findClientesActivosByEmpresa(@Param("idEmpresa") Integer idEmpresa, Pageable pageable);



}
