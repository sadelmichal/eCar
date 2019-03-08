package com.michalsadel.ecar.domain;

import org.springframework.data.repository.*;

import java.util.*;

interface CustomerRepository extends Repository<Customer, Long> {
    Optional<Customer> findById(Long id);

    Customer save(Customer customer);

    void deleteAll();
}
