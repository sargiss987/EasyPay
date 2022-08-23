package com.example.easypay.exception.handler;

import static com.example.easypay.constants.ExceptionHandlingConstants.REDIRECT_TO_LOGIN;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView illegalArgumentExceptionHandler() {
        return new ModelAndView(REDIRECT_TO_LOGIN);
    }
}
