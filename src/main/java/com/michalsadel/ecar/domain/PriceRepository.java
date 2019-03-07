package com.michalsadel.ecar.domain;

import org.springframework.data.repository.*;

import java.util.*;

interface PriceRepository extends Repository<Price, String> {
    List<Price> findAll();

    Price save(Price price);
}
