package com.navadhi.employeeservice.exception;

import com.navadhi.employeeservice.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailAlreadyExistException(
            EmailAlreadyExistsException exception,
            WebRequest request
    ) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                LocalDateTime.now(), HttpStatus.BAD_REQUEST,
                exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest request
    ) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                LocalDateTime.now(), HttpStatus.NOT_FOUND,
                exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        errors.forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMap.put(fieldName, message);
        });
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }


}
