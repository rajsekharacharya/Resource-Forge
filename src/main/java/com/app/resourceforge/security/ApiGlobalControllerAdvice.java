package com.app.resourceforge.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ApiGlobalControllerAdvice {

    // Exception handling method for all exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }

    // ModelAttribute method for adding global attributes to the model
    @ModelAttribute
    public void globalAttributes(Model model) {
        model.addAttribute("apiVersion", "v1");
    }
}