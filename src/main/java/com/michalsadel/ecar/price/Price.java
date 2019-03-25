package com.michalsadel.ecar.price;

import com.michalsadel.ecar.Entity;
import com.michalsadel.ecar.price.dto.*;
import lombok.*;

import java.math.*;
import java.time.*;
import java.time.temporal.*;

import static java.util.Objects.*;

@javax.persistence.Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Price extends Entity<Long> {
    private BigDecimal perMinute;
    private LocalTime effectSince;
    private LocalTime effectUntil;

    boolean isDefaultPrice() {
        return effectSince.equals(LocalTime.MIDNIGHT) && effectUntil.truncatedTo(ChronoUnit.SECONDS).equals(LocalTime.MAX.truncatedTo(ChronoUnit.SECONDS));
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




