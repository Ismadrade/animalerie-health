package br.com.ismadrade.petmanagement.exceptions.handlers;

import br.com.ismadrade.petmanagement.exceptions.ApiError;
import br.com.ismadrade.petmanagement.exceptions.CustomException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(CustomException ex) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(ex.getHttpStatus())
                .message(ex.getErrorMessage()).build();

        return ResponseEntity.status(ex.getHttpStatus())
                .body(error);
    }
}
