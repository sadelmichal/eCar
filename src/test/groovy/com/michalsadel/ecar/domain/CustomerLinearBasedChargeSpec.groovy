package com.michalsadel.ecar.domain

class CustomerLinearBasedChargeSpec extends CustomerSpec {
    TestConfiguration config() {
        return new TestConfiguration(LinearBasedCalculator)
    }
}