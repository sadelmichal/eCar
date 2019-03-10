package com.michalsadel.ecar.price;

import org.springframework.data.repository.*;

import java.util.*;

interface PriceRepository extends Repository<Price, Long> {
    List<Price> findAll();

    Price save(Price price);

    void deleteAll();
}
