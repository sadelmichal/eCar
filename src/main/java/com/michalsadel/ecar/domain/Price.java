package com.michalsadel.ecar.domain;

import lombok.*;

import javax.persistence.*;
import java.math.*;
import java.time.*;

@Entity
@Data
class Price {
    @Id
    private String id;
    private BigDecimal perMinute;
    private LocalTime effectSince;
    private LocalTime effectUntil;
}
