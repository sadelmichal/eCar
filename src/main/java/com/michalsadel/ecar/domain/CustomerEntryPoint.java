package com.michalsadel.ecar.domain;

import com.michalsadel.ecar.dto.*;
import org.springframework.transaction.annotation.*;

import java.math.*;
import java.time.*;

import static java.util.Objects.*;


@Transactional
public class CustomerEntryPoint {
    private final CustomerRepository customerRepository;
    private final ChargeCalculator chargeCalculator;

    CustomerEntryPoint(CustomerRepository customerRepository, ChargeCalculator chargeCalculator) {
        this.customerRepository = customerRepository;
        this.chargeCalculator = chargeCalculator;
    }

    public CustomerDto add(CustomerDto customerDto) {
        requireNonNull(customerDto);
        Customer customer = Customer.builder()
                .id(customerDto.getId())
                .customerType(CustomerType.valueOf(customerDto.getCustomerType().name()))
                .build();
        customer = customerRepository.save(customer);
        return CustomerDto.builder()
                .customerType(CustomerTypeDto.valueOf(customer.getCustomerType().name()))
                .id(customer.getId())
                .build();
    }

    public void removeAll() {
        customerRepository.deleteAll();
    }


    public BigDecimal charge(LocalDateTime startTime, LocalDateTime finishTime, Long customerId) {
        final BigDecimal charge = chargeCalculator.calculate(startTime, finishTime);
        Customer customer = customerRepository
                .findById(customerId)
                .orElse(Customer
                        .builder()
                        .customerType(CustomerType.DEFAULT)
                        .build());
        return DiscountContext.from(customer.getCustomerType()).apply(charge);
    }
}
