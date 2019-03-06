package com.michalsadel.ecar.domain;

import org.springframework.data.repository.*;

interface CustomerRepository extends Repository<Customer, Long> {
    Customer findById(Long id);
}
