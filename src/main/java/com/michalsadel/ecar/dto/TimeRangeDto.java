package com.michalsadel.ecar.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimeRangeDto {
    @NotNull
    private LocalTime startsAt;
    @NotNull
    private LocalTime finishesAt;
}
