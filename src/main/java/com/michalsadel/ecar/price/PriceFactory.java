package com.michalsadel.ecar.price;

import com.michalsadel.ecar.price.dto.PriceDto;

interface PriceFactory {
    Price from(PriceDto priceDto);
}
