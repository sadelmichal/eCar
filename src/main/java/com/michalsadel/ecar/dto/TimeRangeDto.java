package com.michalsadel.ecar.dto;

import lombok.*;

import java.time.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimeRangeDto {
    private LocalTime startsAt;
    private LocalTime finishesAt;
}
