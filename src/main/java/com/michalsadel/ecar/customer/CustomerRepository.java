package com.michalsadel.ecar.customer;

import org.springframework.data.repository.*;

import java.util.*;

interface CustomerRepository extends Repository<Customer, Long> {
    Optional<Customer> findById(Long id);

    List<Customer> findAll();

    Customer save(Customer customer);

    void deleteAll();
}
