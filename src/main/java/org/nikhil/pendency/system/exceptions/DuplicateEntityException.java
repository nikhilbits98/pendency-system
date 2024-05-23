package org.nikhil.pendency.system.exceptions;

public class DuplicateEntityException extends RuntimeException{
    public DuplicateEntityException(String message){
        super(message);
    }
}
