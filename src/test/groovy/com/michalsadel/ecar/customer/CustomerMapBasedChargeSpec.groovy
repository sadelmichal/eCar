package com.michalsadel.ecar.customer


import com.michalsadel.ecar.charge.ChargeFacade
import com.michalsadel.ecar.charge.ChargeTestConfiguration
import com.michalsadel.ecar.customer.CustomerSpec

class CustomerMapBasedChargeSpec extends CustomerSpec {
    @Override
    ChargeFacade chargeFacade() {
        return new ChargeTestConfiguration().chargeFacadeWithMapCalculator()
    }
}