package com.michalsadel.ecar.price;

import com.michalsadel.ecar.utils.ReflectionUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class FakePriceRepository implements PriceRepository {
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
