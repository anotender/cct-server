package com.cct.controller.exception;

import com.cct.exception.BadRequestException;
import com.cct.exception.ErrorInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.cct.exception.ErrorInfo.UNKNOWN;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorInfo> handleBadRequestException(BadRequestException e) {
        e.printStackTrace();
        return ResponseEntity.status(e.getErrorInfo().getStatus()).body(e.getErrorInfo());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorInfo> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(UNKNOWN.getStatus()).body(UNKNOWN);
    }

}
