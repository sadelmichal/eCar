package com.michalsadel.ecar.dto;

import lombok.*;

import java.time.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimeRangeDto {
    @NonNull
    private LocalTime startsAt;
    @NonNull
    private LocalTime finishesAt;
}
