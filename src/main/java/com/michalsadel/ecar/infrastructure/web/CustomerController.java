package com.michalsadel.ecar.infrastructure.web;

import com.michalsadel.ecar.charge.dto.ChargeDto;
import com.michalsadel.ecar.customer.CustomerFacade;
import com.michalsadel.ecar.customer.dto.CustomerDto;
import com.michalsadel.ecar.price.dto.DateTimeRangeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
class CustomerController {
    private final CustomerFacade customerFacade;

    CustomerController(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    @PostMapping("/customers/{customerId}/charge")
    @ResponseBody
    ChargeDto charge(@PathVariable Long customerId, @RequestBody @Valid DateTimeRangeDto dateTimeRangeDto) {
        final BigDecimal charge = customerFacade.charge(dateTimeRangeDto.getStart(), dateTimeRangeDto.getFinish(), customerId);
        return ChargeDto.builder().charge(charge).build();
    }

    @PostMapping("/customers")
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
