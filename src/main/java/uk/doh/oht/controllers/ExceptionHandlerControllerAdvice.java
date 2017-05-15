package uk.doh.oht.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by peterwhitehead on 09/05/2017.
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String handleException(final Exception exception) {
        log.error("Unrecoverable error - " + exception.getMessage(), exception);
        return "error";
    }
}
