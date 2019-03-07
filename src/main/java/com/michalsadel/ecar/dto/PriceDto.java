package com.michalsadel.ecar.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.math.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PriceDto {
    @NotNull
    private BigDecimal perMinute;
    @Valid
    private TimeRangeDto effectedIn;
    @JsonIgnore
    private transient Boolean defaultInSystem;
}
