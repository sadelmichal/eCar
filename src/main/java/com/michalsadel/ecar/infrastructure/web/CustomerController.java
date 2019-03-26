package com.michalsadel.ecar.infrastructure.web;

import com.michalsadel.ecar.charge.dto.*;
import com.michalsadel.ecar.customer.*;
import com.michalsadel.ecar.customer.dto.*;
import com.michalsadel.ecar.price.dto.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.math.*;
import java.util.*;

@RestController
class CustomerController {
    private final CustomerFacade customerFacade;

    CustomerController(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    @PostMapping("/customer/{customerId}/charge")
    @ResponseBody
    ChargeDto charge(@PathVariable Long customerId, @RequestBody @Valid DateTimeRangeDto dateTimeRangeDto) {
        final BigDecimal charge = customerFacade.charge(dateTimeRangeDto.getStart(), dateTimeRangeDto.getFinish(), customerId);
        return ChargeDto.builder().charge(charge).build();
    }

    @PostMapping("/customer")
    @ResponseBody
    CustomerDto addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return customerFacade.add(customerDto);
    }

    @DeleteMapping("/customers")
    ResponseEntity removeCustomers() {
        customerFacade.removeAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/customers")
    List<CustomerDto> getCustomers() {
        return customerFacade.findAll();
    }

}
