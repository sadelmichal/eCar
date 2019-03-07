package com.michalsadel.ecar.dto;

import lombok.*;

import java.math.*;

@Data
@Builder
public class ChargeDto {
    private BigDecimal charge;
}
