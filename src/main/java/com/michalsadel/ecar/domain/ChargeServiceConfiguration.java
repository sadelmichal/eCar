package com.michalsadel.ecar.domain;

import org.springframework.context.annotation.*;

@Configuration
class ChargeServiceConfiguration {
    @Bean
    PriceFactory priceFactory() {
        return new DefaultPriceFactory();
    }

    @Bean
    ChargeService chargingStationCalculator(PriceRepository priceRepository, CustomerRepository customerRepository, PriceFactory priceFactory) {
        return new ChargeService(priceRepository, customerRepository, priceFactory);
    }

}
