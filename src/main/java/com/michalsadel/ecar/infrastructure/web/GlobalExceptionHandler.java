package com.michalsadel.ecar.infrastructure.web;

import com.fasterxml.jackson.databind.exc.*;
import com.michalsadel.ecar.exceptions.*;
import com.michalsadel.ecar.infrastructure.dto.*;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.format.*;

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
