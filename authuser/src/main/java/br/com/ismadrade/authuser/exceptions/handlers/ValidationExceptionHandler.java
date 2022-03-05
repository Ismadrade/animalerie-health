package br.com.ismadrade.authuser.exceptions.handlers;

import br.com.ismadrade.authuser.exceptions.ApiError;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ApiError>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        List<ApiError> errors = new ArrayList<>();
        BindingResult results = ex.getBindingResult();

        for (FieldError e: results.getFieldErrors()) {
            errors.add(ApiError.builder()
                    .field(e.getField())
                    .timestamp(LocalDateTime.now())
                    .value(e.getRejectedValue())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getDefaultMessage()).build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}
