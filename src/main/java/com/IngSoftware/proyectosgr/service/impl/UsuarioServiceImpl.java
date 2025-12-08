package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.config.exception.ResourceNotFoundException;
import com.IngSoftware.proyectosgr.domain.dto.User.UserInformationResource;
import com.IngSoftware.proyectosgr.domain.mapping.UserMapper;
import com.IngSoftware.proyectosgr.domain.model.Usuario;
import com.IngSoftware.proyectosgr.domain.repository.UsuarioRepository;
import com.IngSoftware.proyectosgr.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usersRepository;

    @Autowired
    UserMapper userMapper;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Optional<Usuario> getUserByEmail(String email) {
        Optional<Usuario> optionalUser = this.usersRepository.getUserByEmail(email);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("Usuario", email);
        }
        return optionalUser;
    }

    @Override
    public UserInformationResource getUserInformation() {
        // Obtener usuario autenticado del token JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        logger.info("Obteniendo información del usuario autenticado: {}", username);

        Usuario usuario = usersRepository.findByUsuario(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "usuario", username));

        // Convertir a resource con el mapper
        UserInformationResource userInfo = userMapper.toInformationResource(usuario);

        logger.info("Información generada correctamente para {} - Rol: {}", username, userInfo.getRol());

        return userInfo;
    }


    // ============================================
    // MÉTODOS PARA CONSULTORES (idTipoUsuario = 1)
    // ============================================

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getAllConsultores() {
        return usersRepository.findAllConsultores();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> getAllConsultores(Pageable pageable) {
        return usersRepository.findAllConsultores(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getConsultoresActivos() {
        return usersRepository.findConsultoresActivos();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> getConsultoresActivos(Pageable pageable) {
        return  usersRepository.findConsultoresActivos(pageable);
    }

    // ============================================
    // MÉTODOS PARA CLIENTES (idTipoUsuario = 2)
    // ============================================

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getAllClientes() {
        return usersRepository.findAllClientes();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> getAllClientes(Pageable pageable) {
        return usersRepository.findAllClientes(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> searchClientesByNombre(String nombre, Pageable pageable) {
        return usersRepository.searchClientesByNombres(nombre, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> searchConsultoresByNombre(String nombre, Pageable pageable) {
        return usersRepository.searchConsultoresByNombres(nombre, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getClientesActivos() {
        return usersRepository.findClientesActivos();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> getClientesActivos(Pageable pageable) {
        return usersRepository.findClientesActivos(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getClientesByEmpresa(Integer idEmpresa) {
        return usersRepository.findClientesByEmpresa(idEmpresa);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> getClientesByEmpresa(Integer idEmpresa, Pageable pageable) {
        return usersRepository.findClientesByEmpresa(idEmpresa, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getClientesActivosByEmpresa(Integer idEmpresa) {
        return usersRepository.findClientesActivosByEmpresa(idEmpresa);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> getClientesActivosByEmpresa(Integer idEmpresa, Pageable pageable) {
        return usersRepository.findClientesActivosByEmpresa(idEmpresa, pageable);
    }

}
