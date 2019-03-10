package com.michalsadel.ecar.customer;

import com.michalsadel.ecar.charge.*;
import com.michalsadel.ecar.price.*;
import org.springframework.context.annotation.*;

@Configuration
class CustomerConfiguration {
    @Bean
    CustomerFacade customerService(CustomerRepository customerRepository, ChargeFacade chargeFacade, PriceFacade priceFacade) {
        return new CustomerFacade(customerRepository, chargeFacade, priceFacade);
    }
}
