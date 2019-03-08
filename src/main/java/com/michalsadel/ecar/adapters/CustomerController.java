package com.michalsadel.ecar.adapters;

import com.michalsadel.ecar.domain.*;
import com.michalsadel.ecar.dto.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.math.*;

@RestController
class CustomerController {
    private final CustomerEntryPoint customerEntryPoint;

    CustomerController(CustomerEntryPoint customerEntryPoint) {
        this.customerEntryPoint = customerEntryPoint;
    }

    @PostMapping("/customer/{customerId}/charge")
    @ResponseBody
    ChargeDto charge(@PathVariable Long customerId, @RequestBody @Valid DateTimeRangeDto dateTimeRangeDto) {
        final BigDecimal charge = customerEntryPoint.charge(dateTimeRangeDto.getStart(), dateTimeRangeDto.getFinish(), customerId);
        return ChargeDto.builder().charge(charge).build();
    }

    @PostMapping("/customer")
    @ResponseBody
    CustomerDto addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return customerEntryPoint.add(customerDto);
    }

    @DeleteMapping("/customers")
    ResponseEntity removeCustomers() {
        customerEntryPoint.removeAll();
        return ResponseEntity.ok().build();
    }

}
