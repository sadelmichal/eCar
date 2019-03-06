package com.michalsadel.ecar.domain;

import org.mapstruct.factory.*;
import org.springframework.context.annotation.*;

@Configuration
class ChargeServiceConfiguration {

    @Bean
    ChargeService chargingStationCalculator(PriceRepository priceRepository) {
        return new ChargeService(priceRepository, Mappers.getMapper(PriceMapper.class));
    }

}
