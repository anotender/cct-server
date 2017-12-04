package com.cct.controller.exception;

import com.cct.exception.BadRequestException;
import com.cct.exception.ErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.cct.exception.ErrorInfo.UNKNOWN;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorInfo> handleBadRequestException(BadRequestException e) {
        LOGGER.error(e.getErrorInfo().getCode() + ": " + e.getErrorInfo().getMessage());
        return ResponseEntity.status(e.getErrorInfo().getStatus()).body(e.getErrorInfo());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorInfo> handleException(Exception e) {
        LOGGER.error(UNKNOWN.getCode() + ": " + e.getMessage());
        return ResponseEntity.status(UNKNOWN.getStatus()).body(UNKNOWN);
    }

}
