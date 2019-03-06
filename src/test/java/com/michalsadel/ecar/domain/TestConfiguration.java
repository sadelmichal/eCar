package com.michalsadel.ecar.domain;

class TestConfiguration {
    ChargeService chargingStationCalculator() {
        return new ChargeService(new FakePriceRepository(), new FakeCustomerRepository(), new DefaultPriceFactory());
    }
}
