package com.michalsadel.ecar.customer;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface CustomerRepository extends Repository<Customer, Long> {
    Optional<Customer> findById(Long id);

    List<Customer> findAll();

    Customer save(Customer customer);

    void deleteAll();
}
