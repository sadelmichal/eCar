package com.michalsadel.ecar.customer;

import com.michalsadel.ecar.customer.dto.*;
import lombok.*;

import java.math.*;

@javax.persistence.Entity
@Builder
@RequiredArgsConstructor
class Customer extends Entity<Long> {
    private final CustomerType customerType;

    BigDecimal discount(BigDecimal charge) {
        return customerType.getChargeStrategy().apply(charge);
    }

    static Customer defaultCustomer() {
        return Customer
                .builder()
                .customerType(CustomerType.DEFAULT)
                .build();
    }

    CustomerDto toDto() {
        return CustomerDto.builder()
                .customerType(CustomerTypeDto.valueOf(customerType.name()))
                .id(getId())
                .build();
    }
}
