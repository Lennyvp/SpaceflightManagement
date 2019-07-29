package com.lenart.spaceflightmanagement.service;

import com.lenart.spaceflightmanagement.model.Tourist;

import java.util.List;

public interface TouristService {

    Tourist findTouristById(Long id);

    List<Tourist> findAllTourists();

    List<Tourist> findAllTouristsByName(String name);

    void save(Tourist tourist);

    void delete(Tourist tourist);
}
