package com.michalsadel.ecar.domain

import com.michalsadel.ecar.exceptions.PriceInvalidTimeRangeException
import com.michalsadel.ecar.exceptions.PriceOverlapsAnotherPriceException
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalTime

class PriceSpec extends Specification implements ServiceSpec {
    def "should have a price with time span extended all day long"(){
        given: "default price without time span"
            def price = createDefaultPrice()
        when: "price is added to the system"
            price = chargeService.add(price)
        then: "time span is created for all day"
            price.effectedIn.startsAt == LocalTime.MIDNIGHT
            price.effectedIn.finishesAt == LocalTime.MAX
        and: "price is default in the system"
            price.defaultInSystem
    }

    def "should throw an exception when time span of a price overlaps non-default price already in the system"(){
        given: "have default price in the system"
            chargeService.add(createDefaultPrice())
        and: "have one non-default price in the system"
            chargeService.add(createPrice("08:00", "16:00"))
        when:
            chargeService.add(createPrice("09:00", "15:00"))
        then:
            thrown(PriceOverlapsAnotherPriceException)
    }

    def "should throw an exception when another default price is already in the system"(){
        given: "have default price in the system"
            chargeService.add(createDefaultPrice())
        when: "add another price without time span"
            chargeService.add(createDefaultPrice())
        then:
            thrown(PriceOverlapsAnotherPriceException)
    }

    @Unroll
    def "should throw an exception when price between #starts and #finishes is added to the system when price between 08:00 and 16:00"(){
        given:
            chargeService.add(createPrice("08:00", "16:00"))
        when:
            chargeService.add(createPrice(starts, finishes))
        then:
            thrown(PriceOverlapsAnotherPriceException)
        where:
            starts      |finishes
            "09:00"     |"15:00"
            "09:00"     |"17:00"
            "03:00"     |"09:00"
            "15:59:59"  |"17:00"
            "07:00"     |"08:00:01"
    }

    @Unroll
    def "should accept price between #starts and #finishes if there is already price between 08:00 and 16:00"(){
        given:
            chargeService.add(createPrice("08:00", "16:00"))
        when:
            chargeService.add(createPrice(starts, finishes))
        then:
            noExceptionThrown()
        where:
            starts      |finishes
            "16:00"     |"17:00"
            "07:00"     |"08:00"
    }

    def "should throw an exception when price definition start time is greater than the end time"(){
        when:
            chargeService.add(createPrice("04:00", "03:00"))
        then:
            thrown(PriceInvalidTimeRangeException)
    }
}