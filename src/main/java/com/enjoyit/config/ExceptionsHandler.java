package com.enjoyit.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.enjoyit.enums.MsgServiceResponse;

@RestControllerAdvice
public class ExceptionsHandler {

    /**
     * AUTHENTICATION EXCEPTION
     */

    @ResponseBody
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(final AuthenticationException ex) {
        final Map<String, String> errors = new HashMap<>();
        if (ex instanceof DisabledException) {
            errors.put("error",MsgServiceResponse.ACCOUNT_IS_DISABLED.toString());
        } else if (ex instanceof LockedException) {
            errors.put("error",MsgServiceResponse.ACCOUNT_IS_LOCKED.toString());
        } else {
            errors.put("error",MsgServiceResponse.AUTHENTICATION_FAILED.toString());

        }

        return new ResponseEntity<Map<String, String>>(errors,HttpStatus.UNAUTHORIZED);
    }

    /**
     * VALIDATION EXCEPTION HANDLERS
     */

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
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgumentException(final IllegalArgumentException ex) {
        final Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Map<String, String> handleServiceLayerDTOException(final MethodArgumentNotValidException ex) {
        final Map<String, String> errors = new HashMap<>();
        if (ex.getBindingResult().hasGlobalErrors()) {
            ex.getBindingResult().getGlobalErrors().forEach(g -> {
                errors.put("error   ", g.getDefaultMessage());
            });
        }
        if (ex.getBindingResult().hasFieldErrors()) {
            ex.getBindingResult().getFieldErrors().forEach(field -> {
                errors.put(field.getField(), field.getDefaultMessage());
            });
        }

        return errors;

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = TransactionSystemException.class)
    public Map<String, String> handleTransactionSystemException(final TransactionSystemException ex) {
        final Throwable cause = ex.getRootCause();
        final Map<String, String> errors = new HashMap<>();
        if (cause instanceof ConstraintViolationException) {

            ((ConstraintViolationException) cause).getConstraintViolations().forEach(violation -> {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            });

            return errors;
        }
        errors.put("Persistence Failed", "Please contact the administrator");
        return errors;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = EntityNotFoundException.class)
    public Map<String, String> handleEntityNotFoundException(final EntityNotFoundException ex) {
        final Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return errors;
    }

}
