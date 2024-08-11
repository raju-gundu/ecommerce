package com.ecommerce.userservice.controlleradvices;

import ch.qos.logback.core.model.Model;
import com.ecommerce.userservice.dtos.ExceptionDTO;
import com.ecommerce.userservice.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleUserNotFoundException(UserNotFoundException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO();
    exceptionDTO.setDetails("Something went wrong");
    exceptionDTO.setMessage(exception.getMessage());
    return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
}
}
