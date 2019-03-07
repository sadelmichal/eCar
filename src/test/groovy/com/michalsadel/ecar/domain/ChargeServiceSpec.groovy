package com.michalsadel.ecar.domain


import com.michalsadel.ecar.dto.PriceDto
import com.michalsadel.ecar.dto.TimeRangeDto
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime
import java.time.LocalTime

class ChargeServiceSpec extends Specification {
    ChargeService chargingService = new TestConfiguration().chagrgeService()

    PriceDto defaultPrice = PriceDto.builder()
            .perMinute(new BigDecimal("0.5"))
            .build()

    PriceDto normalPrice = PriceDto.builder()
            .effectedIn(TimeRangeDto.builder()
                            .startsAt(LocalTime.parse("03:30"))
                            .finishesAt(LocalTime.parse("04:00"))
                        .build())
            .perMinute(BigDecimal.ONE)
            .build()
    PriceDto higherPrice = PriceDto.builder()
            .effectedIn(TimeRangeDto.builder()
                            .startsAt(LocalTime.parse("03:00"))
                            .finishesAt(LocalTime.parse("03:30"))
                        .build())
            .perMinute(BigDecimal.valueOf(2))
            .build()

    final int defaultCustomer = 0
    final int vipCustomer = 42

    static private LocalDateTime time(String time){
        return LocalDateTime.parse(time)
    }

    @Unroll
    def "should be no charge between #started and #finished if there is no price definition available in system"(){
        expect:
            chargingService.calculate(started, finished, defaultCustomer) == 0
        where:
            started <<  [time("2019-01-01T03:00"), time("2019-01-01T03:00"), time("2019-01-02T03:00")]
            finished << [time("2019-01-01T03:30"), time("2019-01-02T04:00"), time("2019-01-01T03:00")]
    }

    @Unroll
    def "should be charged #charge EUR between #started and #finished if price effects all day long with 0.5EUR per minute"(){
        given: "system has one price defined of 0.5 EUR per minute all day long"
            chargingService.add(defaultPrice)
        expect:
            chargingService.calculate(started, finished, defaultCustomer) == charge
        where:
            started <<  [time("2019-01-01T03:00"), time("2019-01-01T03:00"), time("2019-01-02T03:00")]
            finished << [time("2019-01-01T03:30"), time("2019-01-02T04:00"), time("2019-01-01T03:00")]
            charge << [15, 750, 0]
    }

    @Unroll
    def "should be charged #charge EUR between #started and #finished if price effects between 3:30AM and 4:00AM with 1EUR per minute"(){
        given: "system has one price defined of 1 EUR per minute between 3:30AM and 4:00AM"
            chargingService.add(normalPrice)
        expect:
            chargingService.calculate(started, finished, defaultCustomer) == charge
        where:
            started <<  [time("2019-01-01T03:00"), time("2019-01-01T03:00"), time("2019-01-02T03:00")]
            finished << [time("2019-01-01T03:30"), time("2019-01-02T04:00"), time("2019-01-01T03:00")]
            charge << [0, 60, 0]
    }

    @Unroll
    def "should be charged #charge EUR between #started and #finished if price effects between 3:00AM and 3:30AM with 2EUR per minute and between 3:30AM and 4:00AM with 1EUR per minute"(){
        given: "system have two prices 1 EUR per minute between 3:30AM and 4:00AM and 2 EUR per minute between 3:00AM and 3:30AM"
            chargingService.add(normalPrice)
            chargingService.add(higherPrice)
        expect:
            chargingService.calculate(started, finished, defaultCustomer) == charge
        where:
            started <<  [time("2019-01-01T03:00"), time("2019-01-01T03:00"), time("2019-01-02T03:00")]
            finished << [time("2019-01-01T03:30"), time("2019-01-02T04:00"), time("2019-01-01T03:00")]
            charge << [60, 180, 0]
    }

    @Unroll
    def "should be charged #charge EUR between #started and #finished if price effects between 3:00AM and 3:30AM with 2EUR per minute and between 3:30AM and 4:00AM with 1EUR per minute and there is default price of 0.5 EUR in any other time"(){
        given: "system have three prices 1 EUR per minute between 3:30AM and 4:00AM and 2 EUR per minute between 3:00AM and 3:30AM and default one 0.5 EUR in any other time"
            chargingService.add(normalPrice)
            chargingService.add(higherPrice)
            chargingService.add(defaultPrice)
        expect:
            chargingService.calculate(started, finished, defaultCustomer) == charge
        where:
            started <<  [time("2019-01-01T03:00"), time("2019-01-01T03:00"), time("2019-01-02T03:00")]
            finished << [time("2019-01-01T03:30"), time("2019-01-02T04:00"), time("2019-01-01T03:00")]
            charge << [60, 870, 0]
    }

    def "system should apply 10% discount for a VIP customer"(){
        given: "system have one default price of 0.5 EUR all day long"
            chargingService.add(defaultPrice)
        and: "charging started at midnight"
            def startedAt = time("2019-01-01T00:00")
        and: "charging finished at midnight the next day"
            def finishedAt = time("2019-01-02T00:00")
        and: "system calculates normal price for a normal customer"
            def normalCharge = chargingService.calculate(startedAt, finishedAt, defaultCustomer)
        when: "system calculates normal price for a VIP customer"
            def discountedCharge = chargingService.calculate(startedAt, finishedAt, vipCustomer)
        then: "charge for a VIP customer should be 10% lower than charge of a normal customer"
            discountedCharge == normalCharge - (0.1 * normalCharge)
    }

}
