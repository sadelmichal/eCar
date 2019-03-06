package com.michalsadel.ecar.domain;

import com.michalsadel.ecar.dto.*;
import org.mapstruct.*;

@Mapper
interface PriceMapper {
    @Mappings({
            @Mapping(target = "perMinute"),
            @Mapping(target = "effectedIn"),
            @Mapping(target = "effectedIn.startsAt", source = "effectSince"),
            @Mapping(target = "effectedIn.finishesAt", source = "effectUntil")
    })
    PriceDto toDto(Price price);

    @InheritInverseConfiguration
    Price fromDto(PriceDto priceDefinition);
}
