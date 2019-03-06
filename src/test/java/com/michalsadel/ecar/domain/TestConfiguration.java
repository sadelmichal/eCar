package com.michalsadel.ecar.domain;

import org.mapstruct.factory.*;

class TestConfiguration {
    ChargeService chargingStationCalculator() {
        return new ChargeService(new FakePriceRepository(), Mappers.getMapper(PriceMapper.class));
    }
}
