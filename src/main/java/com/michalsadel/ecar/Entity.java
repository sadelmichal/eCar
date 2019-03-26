package com.michalsadel.ecar;

import lombok.AccessLevel;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter(AccessLevel.PUBLIC)
@MappedSuperclass
public abstract class Entity<T> {
    @Id
    @GeneratedValue
    private T id;
}
