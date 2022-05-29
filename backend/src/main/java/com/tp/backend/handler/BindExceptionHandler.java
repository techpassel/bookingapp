package com.tp.backend.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class BindExceptionHandler {
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<Map> processUnmergeException(final BindException e) {
        List list = e.getBindingResult().getAllErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        Map<String, List<String>> errorDetails = new HashMap<>();
        errorDetails.put("errors", list);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
