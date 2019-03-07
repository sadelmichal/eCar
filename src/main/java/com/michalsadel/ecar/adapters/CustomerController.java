package com.michalsadel.ecar.adapters;

import com.michalsadel.ecar.domain.*;
import com.michalsadel.ecar.dto.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.math.*;

@RestController
class CustomerController {
    private final ChargeService chargeService;

    CustomerController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @PostMapping("/customer/{customerId}/charge")
    @ResponseBody
    ChargeDto charge(@PathVariable Long customerId, @RequestBody @Valid DateTimeRangeDto dateTimeRangeDto) {
        final BigDecimal charge = chargeService.calculate(dateTimeRangeDto.getStart(), dateTimeRangeDto.getFinish(), customerId);
        return ChargeDto.builder().charge(charge).build();
    }
}
