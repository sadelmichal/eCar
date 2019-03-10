package com.michalsadel.ecar.price;

import com.michalsadel.ecar.price.dto.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.*;

import static java.util.Objects.*;

@Transactional
public class PriceFacade {
    private final PriceRepository priceRepository;
    private final PriceFactory priceFactory;
    private final PriceValidator priceValidator;

    PriceFacade(PriceRepository priceRepository, PriceFactory priceFactory, PriceValidator priceValidator) {
        this.priceRepository = priceRepository;
        this.priceFactory = priceFactory;
        this.priceValidator = priceValidator;
    }

    public PriceDto add(PriceDto priceDto) {
        requireNonNull(priceDto);
        Price price = priceFactory.from(priceDto);
        priceValidator.validate(price);
        price = priceRepository.save(price);
        return price.toDto();
    }

    public List<PriceDto> findAll() {
        return priceRepository
                .findAll()
                .stream()
                .map(Price::toDto)
                .collect(Collectors.toList());
    }

    public void removeAll() {
        priceRepository.deleteAll();
    }
}
