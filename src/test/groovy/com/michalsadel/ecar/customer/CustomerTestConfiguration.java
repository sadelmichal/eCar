package com.michalsadel.ecar.customer;

import com.michalsadel.ecar.charge.*;
import com.michalsadel.ecar.price.*;

class CustomerTestConfiguration {
    private final CustomerRepository customerRepository;

    CustomerTestConfiguration() {
        customerRepository = new FakeCustomerRepository();
    }

    CustomerFacade customerFacade(PriceFacade priceFacade, ChargeFacade chargeFacade) {
        return new CustomerFacade(customerRepository, chargeFacade, priceFacade);
    }
}
