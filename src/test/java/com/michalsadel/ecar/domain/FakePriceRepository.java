package com.michalsadel.ecar.domain;

import java.util.*;
import java.util.concurrent.*;

import static java.util.Objects.*;

class FakePriceRepository implements PriceRepository {
    private ConcurrentHashMap<Long, Price> map = new ConcurrentHashMap<>();

    @Override
    public List<Price> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Price save(Price price) {
        requireNonNull(price);
        ReflectionUtilities.setId(price, (long) map.size());
        map.put(price.getId(), price);
        return price;
    }

    @Override
    public void deleteAll() {
        map.clear();
    }
}
