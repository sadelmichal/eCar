package com.michalsadel.ecar.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.math.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PriceDto {
    @NonNull
    private BigDecimal perMinute;
    private TimeRangeDto effectedIn;
    @JsonIgnore
    private transient Boolean defaultInSystem;
}
