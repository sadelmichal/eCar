package com.michalsadel.ecar.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
class Customer {
    @Id
    private Long id;

    CustomerType customerType() {
        return id == 42 ? CustomerType.VIP : CustomerType.DEFAULT;
    }
}
