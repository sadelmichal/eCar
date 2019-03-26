package com.michalsadel.ecar.customer;

import com.michalsadel.ecar.charge.ChargeFacade;
import com.michalsadel.ecar.price.PriceFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CustomerConfiguration {
    @Bean
    CustomerFacade customerService(CustomerRepository customerRepository, ChargeFacade chargeFacade, PriceFacade priceFacade) {
        return new CustomerFacade(customerRepository, chargeFacade, priceFacade);
    }
}
