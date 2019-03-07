package com.michalsadel.ecar.dto;

import lombok.*;

import java.time.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DateTimeRangeDto {
    @NonNull
    private LocalDateTime start;
    @NonNull
    private LocalDateTime finish;
}
