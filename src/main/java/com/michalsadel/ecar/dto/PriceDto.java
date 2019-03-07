package com.michalsadel.ecar.dto;

import lombok.*;

import java.math.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PriceDto {
    private BigDecimal perMinute;
    private TimeRangeDto effectedIn;
    private transient Boolean defaultInSystem;
}
