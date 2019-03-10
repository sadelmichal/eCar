package com.michalsadel.ecar.domain;

import com.michalsadel.ecar.dto.*;
import lombok.*;
import org.joda.time.*;

import java.math.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.*;

import static java.util.Objects.*;

@javax.persistence.Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Price extends Entity<Long> {
    @Getter(AccessLevel.PACKAGE)
    private BigDecimal perMinute;
    private LocalTime effectSince;
    private LocalTime effectUntil;

    boolean isDefaultPrice() {
        return effectSince.equals(LocalTime.MIDNIGHT) && effectUntil.equals(LocalTime.MAX);
    }

    boolean overlaps(Price price) {
        requireNonNull(price);
        return effectSince.isBefore(price.effectUntil) && effectUntil.isAfter(price.effectSince);
    }

    boolean isRangeValid() {
        return effectUntil.isAfter(effectSince);
    }

    boolean isPerMinuteValid() {
        return perMinute.signum() != -1;
    }

    int effectSinceAsMinuteInDay() {
        return effectSince.get(ChronoField.MINUTE_OF_DAY);
    }

    int effectUntilAsMinuteInDay() {
        return effectUntil.get(ChronoField.MINUTE_OF_DAY);
    }

    Interval intervalAtDate(LocalDate date) {
        org.joda.time.LocalDateTime effectSince = org.joda.time.LocalDateTime.parse(this.effectSince.atDate(date).toString());
        org.joda.time.LocalDateTime effectUntil = org.joda.time.LocalDateTime.parse(this.effectUntil.atDate(date).toString());
        return new Interval(effectSince.toDateTime(), effectUntil.toDateTime());
    }

    PriceDto toDto() {
        return PriceDto.builder()
                .perMinute(perMinute)
                .effectedIn(TimeRangeDto.builder()
                        .startsAt(effectSince)
                        .finishesAt(effectUntil)
                        .build())
                .defaultInSystem(isDefaultPrice())
                .build();
    }

}




