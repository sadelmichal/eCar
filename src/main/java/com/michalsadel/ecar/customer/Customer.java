package com.michalsadel.ecar.customer;

import com.michalsadel.ecar.Entity;
import com.michalsadel.ecar.customer.dto.*;
import lombok.*;

import java.math.*;

@javax.persistence.Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Customer extends Entity<Long> {
    private CustomerType customerType;

    BigDecimal discount(BigDecimal charge) {
        return customerType.getDiscount().apply(charge);
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
