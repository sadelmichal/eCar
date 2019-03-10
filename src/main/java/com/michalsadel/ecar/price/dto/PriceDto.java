package com.michalsadel.ecar.price.dto;

import io.swagger.annotations.*;
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
    @ApiModelProperty(hidden = true)
    private Boolean defaultInSystem;
}
