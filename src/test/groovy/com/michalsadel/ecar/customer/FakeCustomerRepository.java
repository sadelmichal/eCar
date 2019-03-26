package com.michalsadel.ecar.customer;

import com.michalsadel.ecar.utils.ReflectionUtilities;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class FakeCustomerRepository implements CustomerRepository {
    private ConcurrentHashMap<Long, Customer> map = new ConcurrentHashMap<>();

    @Override
    public Optional<Customer> findById(Long id) {
        final Customer customer = map.get(id);
        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        requireNonNull(customer);
        ReflectionUtilities.setId(customer, (long) map.size());
        map.put(customer.getId(), customer);
        return customer;

    }

    @Override
    public void deleteAll() {
        map.clear();
    }
}
