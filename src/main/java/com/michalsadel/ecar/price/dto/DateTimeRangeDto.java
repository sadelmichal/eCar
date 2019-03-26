package com.michalsadel.ecar.price.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DateTimeRangeDto {
    @NotNull
    private LocalDateTime start;
    @NotNull
    private LocalDateTime finish;
}
