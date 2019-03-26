package com.michalsadel.ecar.customer;

import com.michalsadel.ecar.Entity;
import com.michalsadel.ecar.customer.dto.CustomerDto;
import com.michalsadel.ecar.customer.dto.CustomerTypeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@javax.persistence.Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Customer extends Entity<Long> {
    private CustomerType customerType;

    static Customer defaultCustomer() {
        return Customer
                .builder()
                .customerType(CustomerType.DEFAULT)
                .build();
    }

    BigDecimal discount(BigDecimal charge) {
        return customerType.getDiscount().apply(charge);
    }

    CustomerDto toDto() {
        return CustomerDto.builder()
                .customerType(CustomerTypeDto.valueOf(customerType.name()))
                .id(getId())
                .build();
    }
}
