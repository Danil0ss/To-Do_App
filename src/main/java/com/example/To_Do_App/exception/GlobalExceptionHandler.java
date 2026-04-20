package com.example.To_Do_App.exception;

import com.example.To_Do_App.dto.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(EntityNotFoundException ex){
        ErrorResponseDto responceDto=new ErrorResponseDto(404, ex.getMessage(),
                OffsetDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responceDto);
    }

    @ExceptionHandler(UserAccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDenied(UserAccessDeniedException ex){
        ErrorResponseDto responceDto=new ErrorResponseDto(403, ex.getMessage(),
                OffsetDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responceDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException ex){
        String message=ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponseDto responseDto=new ErrorResponseDto(400,message,
                OffsetDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
