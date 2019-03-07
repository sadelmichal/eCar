package com.michalsadel.ecar.domain;

class TestConfiguration {
    ChargeService chagrgeService() {
        return new ChargeService(new FakePriceRepository(), new FakeCustomerRepository(), new DefaultPriceFactory());
    }
}
