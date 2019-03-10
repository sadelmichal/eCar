package com.michalsadel.ecar.domain;

import com.michalsadel.ecar.dto.*;
import lombok.*;

import java.math.*;

@javax.persistence.Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Customer extends Entity<Long> {
    private CustomerType customerType;

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
