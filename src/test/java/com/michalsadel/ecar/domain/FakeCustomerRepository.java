package com.michalsadel.ecar.domain;

import java.util.*;
import java.util.concurrent.*;

import static java.util.Objects.requireNonNull;

class FakeCustomerRepository implements CustomerRepository {
    private ConcurrentHashMap<Long, Customer> map = new ConcurrentHashMap<>();

    @Override
    public Optional<Customer> findById(Long id) {
        final Customer customer = map.get(id);
        return Optional.ofNullable(customer);
    }

    @Override
    public Customer save(Customer customer) {
        requireNonNull(customer);
        customer.setId(Optional.ofNullable(customer.getId()).orElse((long) map.size()));
        map.put(customer.getId(), customer);
        return customer;

    }

    @Override
    public void deleteAll() {
        map.clear();
    }
}
