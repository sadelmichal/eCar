package com.michalsadel.ecar.infrastructure.web;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.michalsadel.ecar.infrastructure.web.dto.ErrorDto;
import com.michalsadel.ecar.price.exceptions.PriceInvalidTimeRangeException;
import com.michalsadel.ecar.price.exceptions.PriceOverlapsAnotherPriceException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler({PriceOverlapsAnotherPriceException.class, PriceInvalidTimeRangeException.class})
    ResponseEntity<ErrorDto> handleError(Exception exception) {
        return ResponseEntity.badRequest()
                .body(ErrorDto
                        .builder()
                        .error(exception.getLocalizedMessage())
                        .build());
    }

    @ExceptionHandler({InvalidFormatException.class})
    ResponseEntity<ErrorDto> handleInvalidFormatError(Exception exception) {
        if (NestedExceptionUtils.getMostSpecificCause(exception) instanceof DateTimeParseException) {
            return ResponseEntity.badRequest()
                    .body(ErrorDto
                            .builder()
                            .error("Time format is HH:mm and DateTime format is ISO8061")
                            .build());
        }
        return handleError(exception);
    }
}
