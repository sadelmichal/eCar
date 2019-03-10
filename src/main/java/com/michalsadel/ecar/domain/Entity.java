package com.michalsadel.ecar.domain;

import lombok.*;

import javax.persistence.*;

@Getter(AccessLevel.PROTECTED)
@MappedSuperclass
abstract class Entity<T> {
    @Id
    @GeneratedValue
    private T id;
}
