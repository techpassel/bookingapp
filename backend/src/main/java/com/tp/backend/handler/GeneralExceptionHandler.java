package com.tp.backend.handler;

import com.tp.backend.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GeneralExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> processUnmergeException(final Exception e) {
        String msg = "Error in processing request : "+e.getMessage();
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }
}
