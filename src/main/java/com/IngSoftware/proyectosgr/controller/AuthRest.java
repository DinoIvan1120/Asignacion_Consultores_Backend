package com.IngSoftware.proyectosgr.controller;

import com.IngSoftware.proyectosgr.domain.dto.Jwt.ChangePasswordResource;
import com.IngSoftware.proyectosgr.domain.dto.Jwt.LoginResource;
import com.IngSoftware.proyectosgr.domain.dto.Jwt.TokenResource;
import com.IngSoftware.proyectosgr.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthRest {
    @Autowired
    private AuthService authService;
    @PostMapping(path = "/login", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TokenResource> login(@Valid @RequestBody LoginResource loginResource) throws Exception{
        TokenResource tokenResource = this.authService.login(loginResource);
        return new ResponseEntity<>(tokenResource, HttpStatus.OK);
    }
    @PostMapping(path = "/requestPasswordChange/{email}", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> changePassword(@PathVariable String email) throws Exception{
        String message = this.authService.requestPasswordChange(email);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @PostMapping(path = "/changePassword", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordResource request) throws Exception{
        String message = this.authService.updatePassword(request);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}