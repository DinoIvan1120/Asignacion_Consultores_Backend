package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.config.exception.ResourceNotFoundException;
import com.IngSoftware.proyectosgr.config.security.JwtProvider;
import com.IngSoftware.proyectosgr.domain.dto.Citizen.CreateCitizenResource;
import com.IngSoftware.proyectosgr.domain.dto.Jwt.ChangePasswordResource;
import com.IngSoftware.proyectosgr.domain.dto.Jwt.LoginResource;
import com.IngSoftware.proyectosgr.domain.dto.Jwt.TokenResource;
import com.IngSoftware.proyectosgr.domain.enumeration.Rolename;
import com.IngSoftware.proyectosgr.domain.model.*;
import com.IngSoftware.proyectosgr.domain.repository.*;
import com.IngSoftware.proyectosgr.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AuthServiceImpl implements AuthService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CitizenRepository citizenRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RolesRepository roleRepository;
    @Autowired
    private UsuarioRepository usersRepository;
    @Autowired
    private ChangePasswordTokenServiceImpl changePasswordTokenService;
    @Autowired
    private ChangePasswordTokenRepository changePasswordTokenRepository;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider provider;
    @Override
    public Roles getCitizenRole() throws Exception {
        Optional<Roles> optionalRole = this.roleRepository.findByName(Rolename.Cliente);
        if(optionalRole.isEmpty()){
            throw new ResourceNotFoundException(String.format("No existe un role llamado %s", Rolename.Cliente));
        }
        return optionalRole.get();
    }

    @Override
    public Roles getCitizenAllowedRole(Rolename role) throws Exception {

        if(role == Rolename.Administrador){
            throw new ResourceNotFoundException(String.format("No se puede poner rol Administrador %s", Rolename.Administrador));

        }

        return roleRepository.findByName(role)
                .orElseThrow(()-> new ResourceNotFoundException(String.format("No existe un role llamado %s", role)));
    }



    @Override
    public Roles getAdminRole() throws Exception {
        Optional<Roles> optionalRole = this.roleRepository.findByName(Rolename.Administrador);
        if(optionalRole.isEmpty()){
            throw new ResourceNotFoundException(String.format("No existe un role llamado %s", Rolename.Administrador));
        }
        return optionalRole.get();
    }

    @Override
    public Citizen registerCitizen(Citizen request, CreateCitizenResource resource) throws Exception {
        Set<Roles> roleSet = new HashSet<>();
       if(resource.getRole() !=null && !resource.getRole().trim().isEmpty()){
           try{
               Rolename rolename = Rolename.valueOf(resource.getRole());
               roleSet.add(getCitizenAllowedRole(rolename));
           }catch (IllegalArgumentException e){
               throw new ResourceNotFoundException("El rol enviado no existe: " + resource.getRole());
           }

       }else{
           roleSet.add(getCitizenAllowedRole(Rolename.Cliente));
       }
        //roleSet.add(getCitizenRole());
        request.setClave(passwordEncoder.encode(request.getClave()));
        request.setRoles(roleSet);
        return citizenRepository.save(request);
    }

    @Override
    public Admin registerAdmin(Admin request) throws Exception {
        Set<Roles> roleSet = new HashSet<>();
        roleSet.add(getAdminRole());
        request.setClave(passwordEncoder.encode(request.getClave()));
        request.setRoles(roleSet);
        return adminRepository.save(request);
    }

    @Override
    public TokenResource login(LoginResource loginResource) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginResource.getEmail(), loginResource.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = String.format("Bearer %s", provider.generateToken(authentication));
        TokenResource tokenResource = new TokenResource(token);
        return tokenResource;
    }

    @Override
    public String requestPasswordChange(String email) throws Exception {
        String message = "";
        Optional<Usuario> optionalUser = this.usersRepository.getUserByEmail(email);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("Usuario", email);
        }
        Usuario user = optionalUser.get();
        Integer userId = user.getIdUsuario();
        this.changePasswordTokenRepository.deleteAllByUsers_IdUsuario(userId);
        Integer token = this.changePasswordTokenService.generateNewToken();

        ChangePasswordToken changePasswordToken = new ChangePasswordToken();
        changePasswordToken.setUsers(user);
        changePasswordToken.setToken(token);
        this.changePasswordTokenRepository.save(changePasswordToken);

        this.emailService.sendEmail(userId);
        message = String.format("Se envió el código de verificación al correo %s", user.getCorreo());
        return message;
    }

    @Override
    public String updatePassword(ChangePasswordResource request) throws Exception {
        String message = "";
        Optional<ChangePasswordToken> optionalChangePasswordToken = this.changePasswordTokenRepository.findByToken(request.getToken());
        if (optionalChangePasswordToken.isEmpty()){
            throw new RuntimeException(String.format("El token %s no es valido", request.getToken()));
        }
         ChangePasswordToken passwordToken = optionalChangePasswordToken.get();
        //if(!passwordToken.getUsers().getEmail().equals(request.getEmail())) {
            //throw new RuntimeException("Los emails no coinciden.");
        //}
        Optional<Usuario> optionalUser = this.usersRepository.findById(passwordToken.getUsers().getIdUsuario());

        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("Usuario", passwordToken.getUsers().getIdUsuario());
        }
        Usuario user = optionalUser.get();
        user.setClave(passwordEncoder.encode(request.getNewPassword()));
        // Enviar correo de confirmación
        emailService.sendPasswordChangedEmail(user.getIdUsuario(), request.getNewPassword());
        message = String.format("Se actualizó correctamente la contraseña del usuario con id %d", user.getIdUsuario());
        return message;
    }


}
