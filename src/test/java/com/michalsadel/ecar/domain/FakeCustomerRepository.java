package com.michalsadel.ecar.domain;

class FakeCustomerRepository implements CustomerRepository {
    @Override
    public Customer findById(Long id) {
        return Customer.builder()
                .id(id)
                .build();
    }
}
