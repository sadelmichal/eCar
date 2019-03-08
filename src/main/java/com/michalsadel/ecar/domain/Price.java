package com.michalsadel.ecar.domain;

import lombok.*;

import javax.persistence.*;
import java.math.*;
import java.time.*;

import static java.util.Objects.requireNonNull;

@Entity
@Builder
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
class Price {
    @Id
    @Setter(AccessLevel.PACKAGE)
    @GeneratedValue
    private Long id;
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
}
