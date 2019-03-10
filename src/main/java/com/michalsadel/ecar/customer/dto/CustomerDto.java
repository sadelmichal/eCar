package com.michalsadel.ecar.customer.dto;

import io.swagger.annotations.*;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    @ApiModelProperty(hidden = true)
    private Long id;
    @NotNull
    private CustomerTypeDto customerType;
}
