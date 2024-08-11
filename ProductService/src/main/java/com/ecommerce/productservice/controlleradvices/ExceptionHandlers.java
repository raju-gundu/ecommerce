package com.ecommerce.productservice.controlleradvices;

import com.ecommerce.productservice.dtos.ArithmeticExceptionDto;
import com.ecommerce.productservice.dtos.ExceptionDto;
import com.ecommerce.productservice.exceptions.ProductNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ArithmeticExceptionDto> handleArithmeticException() {
        ArithmeticExceptionDto arithmeticExceptionDto = new ArithmeticExceptionDto();
        arithmeticExceptionDto.setMessage("Something has gone wrong");
        return new ResponseEntity<>(arithmeticExceptionDto, HttpStatus.OK);
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<Void> handleArrayIndexOutOfBoundException() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(ProductNotExistsException.class)
    public ResponseEntity<ExceptionDto> handleProductNotExistsException(ProductNotExistsException exception) {
        ExceptionDto dto = new ExceptionDto();
        dto.setMessage(exception.getMessage());
        dto.setDetail("Check the product id. It probably doesn't exist.");

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> genericException(Exception e) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
