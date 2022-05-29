package com.tp.backend.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ConstraintViolationExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<Map> processUnmergeException(final ConstraintViolationException e) {
        List list = e.getConstraintViolations().stream()
                .map(fieldError -> fieldError.getMessage())
                .collect(Collectors.toList());
        Map<String, List<String>> errorDetails = new HashMap<>();
        errorDetails.put("errors", list);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
