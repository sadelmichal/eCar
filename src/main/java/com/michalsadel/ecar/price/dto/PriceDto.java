package com.michalsadel.ecar.price.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
