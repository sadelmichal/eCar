package com.michalsadel.ecar.domain;

class TestConfiguration {
    ChargeService chagrgeService() {
        final FakePriceRepository priceRepository = new FakePriceRepository();
        return new ChargeService(priceRepository, new FakeCustomerRepository(), new DefaultPriceFactory(), new PriceValidator(priceRepository));
    }
}
