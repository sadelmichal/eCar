package com.michalsadel.ecar.domain;

import com.michalsadel.ecar.dto.*;
import org.springframework.transaction.annotation.*;

import static java.util.Objects.*;

@Transactional
public class PriceEntryPoint {
    private final PriceRepository priceRepository;
    private final PriceFactory priceFactory;
    private final PriceValidator priceValidator;

    PriceEntryPoint(PriceRepository priceRepository, PriceFactory priceFactory, PriceValidator priceValidator) {
        this.priceRepository = priceRepository;
        this.priceFactory = priceFactory;
        this.priceValidator = priceValidator;
    }

    public PriceDto add(PriceDto priceDto) {
        requireNonNull(priceDto);
        Price price = priceFactory.from(priceDto);
        priceValidator.validate(price);
        return priceFactory.from(priceRepository.save(price));
    }

    public void removeAll() {
        priceRepository.deleteAll();
    }
}
