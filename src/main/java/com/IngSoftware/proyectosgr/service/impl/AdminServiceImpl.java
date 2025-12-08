    package com.IngSoftware.proyectosgr.service.impl;

    import com.IngSoftware.proyectosgr.config.exception.EmailException;
    import com.IngSoftware.proyectosgr.domain.model.Admin;
    import com.IngSoftware.proyectosgr.domain.repository.AdminRepository;
    import com.IngSoftware.proyectosgr.service.AdminService;
    import com.IngSoftware.proyectosgr.util.DataUtils;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Propagation;
    import org.springframework.transaction.annotation.Transactional;

    @Service
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public class AdminServiceImpl implements AdminService {

        @Autowired
        AdminRepository adminRepository;
        @Autowired
        private AuthServiceImpl authService;

        private final Logger logger = LoggerFactory.getLogger(this.getClass());
        @Override
        public Admin save(Admin admin) throws Exception {
            Boolean validateEmail = DataUtils.validateEmail(admin.getCorreo());
            if(!validateEmail) {
                throw new EmailException(admin.getCorreo());
            }
            Boolean existAlias = this.adminRepository.existsByAlias(admin.getAlias());
            Boolean existEmail = this.adminRepository.existsByCorreo(admin.getCorreo());
            if(Boolean.TRUE.equals(existAlias)){
                throw new RuntimeException(String.format("Ya existe un admin registrado con el alias %s", admin.getAlias()));
            } else if(Boolean.TRUE.equals(existEmail)){
                throw new RuntimeException(String.format("Ya existe un admin registrado con el email %s", admin.getCorreo()));
            }

            admin.setEstado(true);
            return this.authService.registerAdmin(admin);
        }
    }
