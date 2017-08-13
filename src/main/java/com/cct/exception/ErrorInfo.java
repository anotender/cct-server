package com.cct.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorInfo {

    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    UNAUTHORIZED("Unauthorized", HttpStatus.UNAUTHORIZED),
    UNKNOWN("Unknown error", HttpStatus.BAD_REQUEST);

    @Getter
    private final String message;

    @Getter
    private final HttpStatus status;

}