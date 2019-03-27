package com.michalsadel.ecar.price

import com.michalsadel.ecar.utils.ReflectionUtilities

import java.util.ArrayList
import java.util.List
import java.util.concurrent.ConcurrentHashMap

import static java.util.Objects.requireNonNull

class FakePriceRepository implements PriceRepository {
    private ConcurrentHashMap<Long, Price> map = new ConcurrentHashMap<>()

    @Override
    List<Price> findAll() {
        return new ArrayList<>(map.values())
    }

    @Override
    Price save(Price price) {
        requireNonNull(price)
        ReflectionUtilities.setId(price, (long) map.size())
        map.put(price.getId(), price)
        return price
    }

    @Override
    void deleteAll() {
        map.clear()
    }
}
