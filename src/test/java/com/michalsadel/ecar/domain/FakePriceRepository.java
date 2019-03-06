package com.michalsadel.ecar.domain;

import java.util.*;
import java.util.concurrent.*;

import static java.util.Objects.*;

class FakePriceRepository implements PriceRepository {
    private ConcurrentHashMap<String, Price> map = new ConcurrentHashMap<>();

    @Override
    public List<Price> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public String save(Price price) {
        requireNonNull(price);
        price.setId(Optional.ofNullable(price.getId()).orElse(UUID.randomUUID().toString()));
        map.put(price.getId(), price);
        return price.getId();
    }
}
