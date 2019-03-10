package com.michalsadel.ecar.customer


import com.michalsadel.ecar.charge.ChargeFacade
import com.michalsadel.ecar.charge.ChargeTestConfiguration

class CustomerOverlapBasedChargeSpec extends CustomerSpec {
    @Override
    ChargeFacade chargeFacade() {
        return new ChargeTestConfiguration().chargeFacadeWithOverlapCalculator()
    }
}