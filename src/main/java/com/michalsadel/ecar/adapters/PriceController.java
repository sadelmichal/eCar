package com.michalsadel.ecar.adapters;

import com.michalsadel.ecar.domain.*;
import com.michalsadel.ecar.dto.*;
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
    ResponseEntity addPrice(@Valid @RequestBody PriceDto priceDto) {
        chargeService.add(priceDto);
        return ResponseEntity.accepted().build();
    }


}
