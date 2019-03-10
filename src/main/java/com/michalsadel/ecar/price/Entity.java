package com.michalsadel.ecar.price;

import lombok.*;

import javax.persistence.*;

@Getter(AccessLevel.PROTECTED)
@MappedSuperclass
abstract class Entity<T> {
    @Id
    @GeneratedValue
    private T id;
}
