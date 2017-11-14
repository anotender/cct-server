package com.cct.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorInfo {

    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    MAKE_NOT_FOUND("Make not found", HttpStatus.NOT_FOUND),
    MODEL_NOT_FOUND("Model not found", HttpStatus.NOT_FOUND),
    VERSION_NOT_FOUND("Version not found", HttpStatus.NOT_FOUND),
    CAR_NOT_FOUND("Car not found", HttpStatus.NOT_FOUND),
    FUEL_REFILL_NOT_FOUND("Fuel refill not found", HttpStatus.NOT_FOUND),
    FUEL_STATION_NOT_FOUND("Fuel station not found", HttpStatus.NOT_FOUND),
    RATING_NOT_FOUND("Rating not found", HttpStatus.NOT_FOUND),
    UNAUTHORIZED("Unauthorized", HttpStatus.UNAUTHORIZED),
    UNKNOWN("Unknown error", HttpStatus.BAD_REQUEST);

    @Getter
    private final String message;

    @Getter
    private final HttpStatus status;

    @Getter
    private final String code = this.name();

}