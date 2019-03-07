package com.michalsadel.ecar.domain;

import com.michalsadel.ecar.dto.*;

interface PriceFactory {
    Price from(PriceDto priceDto);

    PriceDto from(Price price);
}
