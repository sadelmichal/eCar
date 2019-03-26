package com.michalsadel.ecar.charge.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ChargeDto {
    private BigDecimal charge;
}
