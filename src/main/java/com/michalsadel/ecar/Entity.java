package com.michalsadel.ecar;

import lombok.*;

import javax.persistence.*;

@Getter(AccessLevel.PUBLIC)
@MappedSuperclass
public abstract class Entity<T> {
    @Id
    @GeneratedValue
    private T id;
}
