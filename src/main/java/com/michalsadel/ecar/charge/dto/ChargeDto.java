package com.michalsadel.ecar.charge.dto;

import lombok.*;

import java.math.*;

@Getter
@Builder
public class ChargeDto {
    private BigDecimal charge;
}
