package com.michalsadel.ecar.price;

import com.michalsadel.ecar.price.dto.*;

interface PriceFactory {
    Price from(PriceDto priceDto);
}
