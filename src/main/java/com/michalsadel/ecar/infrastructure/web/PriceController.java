package com.michalsadel.ecar.infrastructure.web;

import com.michalsadel.ecar.price.PriceFacade;
import com.michalsadel.ecar.price.dto.PriceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
class PriceController {
    private final PriceFacade priceFacade;

    PriceController(PriceFacade priceFacade) {
        this.priceFacade = priceFacade;
    }

    @PostMapping("/prices")
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
