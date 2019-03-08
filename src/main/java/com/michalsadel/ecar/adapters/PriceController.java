package com.michalsadel.ecar.adapters;

import com.michalsadel.ecar.domain.*;
import com.michalsadel.ecar.dto.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
class PriceController {
    private final PriceEntryPoint priceEntryPoint;

    PriceController(PriceEntryPoint priceEntryPoint) {
        this.priceEntryPoint = priceEntryPoint;
    }

    @PostMapping("/price")
    ResponseEntity addPrice(@Valid @RequestBody PriceDto priceDto) {
        priceEntryPoint.add(priceDto);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/prices")
    ResponseEntity removePrices() {
        priceEntryPoint.removeAll();
        return ResponseEntity.ok().build();
    }
}
