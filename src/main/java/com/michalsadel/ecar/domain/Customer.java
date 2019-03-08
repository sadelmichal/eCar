package com.michalsadel.ecar.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
class Customer {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.PACKAGE)
    private Long id;
    private CustomerType customerType;
}
