package com.michalsadel.ecar.dto;

import lombok.*;

import java.math.*;

@Getter
@Builder
public class ChargeDto {
    private BigDecimal charge;
}
