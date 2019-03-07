package com.michalsadel.ecar.adapters;

import com.michalsadel.ecar.domain.*;
import com.michalsadel.ecar.dto.*;
import com.michalsadel.ecar.exceptions.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
class PriceController {
    private final ChargeService chargeService;

    public PriceController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @PostMapping("/price")
    ResponseEntity addPrice(@RequestBody @Valid PriceDto priceDto) {
        chargeService.add(priceDto);
        return ResponseEntity.accepted().build();
    }

    @ExceptionHandler({PriceOverlapsAnotherPriceException.class})
    ResponseEntity<ErrorDto> handleError(Exception exception) {
        return ResponseEntity.badRequest()
                .body(ErrorDto
                        .builder()
                        .error(exception.getLocalizedMessage())
                        .build());
    }
}
