package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.config.exception.ResourceNotFoundException;
import com.IngSoftware.proyectosgr.domain.model.ChangePasswordToken;
import com.IngSoftware.proyectosgr.domain.model.Usuario;
import com.IngSoftware.proyectosgr.domain.repository.ChangePasswordTokenRepository;
import com.IngSoftware.proyectosgr.domain.repository.UsuarioRepository;
import com.IngSoftware.proyectosgr.service.EmailService;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UsuarioRepository usersRepository;

    @Autowired
    private ChangePasswordTokenRepository changePasswordTokenRepository;

    @Override
    public void sendEmail(Integer userId) throws Exception {

        Usuario user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", userId));

        String html = getEmailTemplate(userId);

        MimeMessage message = emailSender.createMimeMessage();
        message.setContent(html, "text/html; charset=utf-8");
        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(user.getCorreo()));
        message.setSubject("Proyecto SGR");

        emailSender.send(message);
    }

    @Override
    public String getEmailTemplate(Integer userId) throws Exception {

        Usuario user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", userId));

        ChangePasswordToken passwordToken = changePasswordTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Error al encontrar token de usuario"));

        String templatePath = "html/new-client.html";

        VelocityContext context = new VelocityContext();
        context.put("nombres", user.getNombres());
        context.put("apepaterno", user.getApepaterno());
        context.put("apematerno", user.getApematerno());

        context.put("token", passwordToken.getToken());

        return getContentMail(context, templatePath);
    }

    private String getContentMail(VelocityContext context, String template) {
        VelocityEngine velocity = getVelocityEngine();
        Template t = velocity.getTemplate(template);
        StringWriter w = new StringWriter();
        t.merge(context, w);
        return w.toString();
    }

    private VelocityEngine getVelocityEngine() {
        VelocityEngine velocity = new VelocityEngine();
        velocity.setProperty(Velocity.RESOURCE_LOADER, "classpath");
        velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocity.setProperty("input.encoding", "UTF-8");
        velocity.init();
        return velocity;
    }

    public void sendPasswordChangedEmail(Integer userId, String newPassword) throws Exception {
        Usuario user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", userId));

        String templatePath = "html/password-change.html"; // Nuevo template de confirmación
        VelocityContext context = new VelocityContext();
        context.put("nombres", user.getNombres());
        context.put("apepaterno", user.getApepaterno());
        context.put("apematerno", user.getApematerno());
        context.put("newPassword", newPassword); // opcional, solo si quieres mostrarla

        String html = getContentMail(context, templatePath);

        MimeMessage message = emailSender.createMimeMessage();
        message.setContent(html, "text/html; charset=utf-8");
        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(user.getCorreo()));
        message.setSubject("Contraseña actualizada");

        emailSender.send(message);
    }

}