package com.michalsadel.ecar.customer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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
