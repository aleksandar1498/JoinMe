package com.enjoyit.config;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Map<String, String> handleConstraintValidationException(final ConstraintViolationException ex) {
        final Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        });

        return errors;

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Map<String, String> handleServiceLayerDTOException(final MethodArgumentNotValidException ex) {
        final Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(field -> {
            errors.put(field.getField(), field.getDefaultMessage());
        });
        return errors;

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = TransactionSystemException.class)
    public Map<String, String> handleTransactionSystemException(final TransactionSystemException ex) {
        final Throwable cause = ex.getRootCause();
        if (cause instanceof ConstraintViolationException) {
            final Map<String, String> errors = new HashMap<>();
            ((ConstraintViolationException) cause).getConstraintViolations().forEach(violation -> {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            });

            return errors;
        }
        return new HashMap<String, String>() {
            {
                put("Persistence Failed", "Please contact the administrator");
            }
        };
    }

}
