package com.michalsadel.ecar.domain

class CustomerMapBasedChargeSpec extends CustomerSpec {
    TestConfiguration config() {
        return new TestConfiguration(MapBasedCalculator)
    }
}