package com.michalsadel.ecar.infrastructure.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private String error;
}
