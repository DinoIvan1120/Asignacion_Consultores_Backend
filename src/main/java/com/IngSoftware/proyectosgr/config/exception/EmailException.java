package com.IngSoftware.proyectosgr.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class EmailException extends RuntimeException{
    public EmailException(String email){
        super(String.format("El email %s no es valido.", email));
    }

}
