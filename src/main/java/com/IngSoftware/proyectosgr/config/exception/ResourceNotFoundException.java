package com.IngSoftware.proyectosgr.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String entity, Integer id) {
        super(String.format("%s con id %d no existe.", entity, id));
    }
    public ResourceNotFoundException(String entity, String email) {
        super(String.format("%s con email %s no existe.", entity, email));
    }
    public ResourceNotFoundException(String entity, String field, String value) {
        super(String.format("%s con %s %s no existe.", entity, field, value));
    }
    public ResourceNotFoundException(String message){
        super(message);
    }
}
