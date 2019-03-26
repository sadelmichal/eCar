package com.michalsadel.ecar.infrastructure.web;

import com.michalsadel.ecar.price.*;
import com.michalsadel.ecar.price.dto.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@RestController
class PriceController {
    private final PriceFacade priceFacade;

    PriceController(PriceFacade priceFacade) {
        this.priceFacade = priceFacade;
    }

    @PostMapping("/price")
    ResponseEntity addPrice(@Valid @RequestBody PriceDto priceDto) {
        priceFacade.add(priceDto);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/prices")
    ResponseEntity removePrices() {
        priceFacade.removeAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/prices")
    List<PriceDto> getPrices() {
        return priceFacade.findAll();
    }
}
