package com.michalsadel.ecar.price;

import com.michalsadel.ecar.price.dto.PriceDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Transactional
public class PriceFacade {
    private final PriceRepository priceRepository;
    private final PriceValidator priceValidator;
    private PriceFactory priceFactory;

    PriceFacade(PriceRepository priceRepository, PriceFactory priceFactory, PriceValidator priceValidator) {
        this.priceRepository = priceRepository;
        this.priceFactory = priceFactory;
        this.priceValidator = priceValidator;
    }

    public void setPriceFactory(PriceFactory priceFactory) {
        this.priceFactory = priceFactory;
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
