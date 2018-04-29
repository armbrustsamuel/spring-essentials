package com.dev.essentials.springbootessentials.handler;

import com.dev.essentials.springbootessentials.exception.ResourceNotFoundDetails;
import com.dev.essentials.springbootessentials.exception.ResourceNotFoundException;
import com.dev.essentials.springbootessentials.exception.ValidationExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Samuel on 28/04/18.
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfException){
        ResourceNotFoundDetails rnfDetails = ResourceNotFoundDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resource not found")
                .detail(rnfException.getMessage())
                .developerMessage(rnfException.getClass().getName())
                .build();
        return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException manvException){

        List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

        ValidationExceptionDetail rnfDetails = ValidationExceptionDetail.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Field Validation Error")
                .detail("Field Validation Error")
                .developerMessage(manvException.getClass().getName())
                .field(fields)
                .fieldMessage(fieldMessages)
                .build();
        return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
    }
}
