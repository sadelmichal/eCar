package com.michalsadel.ecar.customer;

import com.michalsadel.ecar.charge.*;
import com.michalsadel.ecar.customer.dto.*;
import com.michalsadel.ecar.price.*;
import com.michalsadel.ecar.price.dto.*;
import org.springframework.transaction.annotation.*;

import java.math.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

import static java.util.Objects.*;


@Transactional
public class CustomerFacade {
    private final CustomerRepository customerRepository;
    private final ChargeFacade chargeFacade;
    private final PriceFacade priceFacade;

    CustomerFacade(CustomerRepository customerRepository, ChargeFacade chargeFacade, PriceFacade priceFacade) {
        this.customerRepository = customerRepository;
        this.chargeFacade = chargeFacade;
        this.priceFacade = priceFacade;
    }

    public CustomerDto add(CustomerDto customerDto) {
        requireNonNull(customerDto);
        Customer customer = Customer.builder()
                .customerType(CustomerType.valueOf(customerDto.getCustomerType().name()))
                .build();
        customer = customerRepository.save(customer);
        return customer.toDto();
    }

    public List<CustomerDto> findAll() {
        return customerRepository
                .findAll()
                .stream()
                .map(Customer::toDto)
                .collect(Collectors.toList());
    }

    public void removeAll() {
        customerRepository.deleteAll();
    }


    public BigDecimal charge(LocalDateTime startTime, LocalDateTime finishTime, Long customerId) {
        final List<PriceDto> prices = priceFacade.findAll();
        final BigDecimal charge = chargeFacade.calculate(startTime, finishTime, prices);
        Customer customer = customerRepository
                .findById(customerId)
                .orElse(Customer.defaultCustomer());
        return customer.discount(charge);
    }
}
