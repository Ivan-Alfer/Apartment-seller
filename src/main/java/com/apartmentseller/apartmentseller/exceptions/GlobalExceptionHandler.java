package com.apartmentseller.apartmentseller.exceptions;

import com.apartmentseller.apartmentseller.services.exceptions.AnnouncementNotFoundException;
import com.apartmentseller.apartmentseller.services.exceptions.UserDoesNotHavePermission;
import com.apartmentseller.apartmentseller.services.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.InvalidParameterException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<GlobalException> handleUserNotFoundException(UserNotFoundException e){
        return new ResponseEntity<>(new GlobalException(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserDoesNotHavePermission.class)
    private ResponseEntity<GlobalException> handleUserDoesNotHavePermission(UserDoesNotHavePermission e) {
        return new ResponseEntity<>(new GlobalException(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidParameterException.class)
    private ResponseEntity<GlobalException> handleInvalidParameterException(InvalidParameterException e) {
        return new ResponseEntity<>(new GlobalException(e.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(AnnouncementNotFoundException.class)
    private ResponseEntity<GlobalException> handleAnnouncementNotFoundException(AnnouncementNotFoundException e){
        return new ResponseEntity<>(new GlobalException(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AnnouncementNotFoundException.class)
    private ResponseEntity<GlobalException> handleAnnouncementNotFoundException(){
        return new ResponseEntity<>(new GlobalException("Announcement not found"), HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    private static class GlobalException{
        private String message;
    }
}
