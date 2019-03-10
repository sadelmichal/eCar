package com.michalsadel.ecar.price.dto;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.*;
import lombok.*;

import javax.validation.constraints.*;
import java.time.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimeRangeDto {
    @NotNull
    @ApiModelProperty(example = "00:00")
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime startsAt;
    @NotNull
    @ApiModelProperty(example = "23:59")
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime finishesAt;
}
