package com.lenart.spaceflightmanagement.service;

import com.lenart.spaceflightmanagement.model.Flight;
import com.lenart.spaceflightmanagement.model.Tourist;

import java.util.List;

public interface TouristService {

    Tourist findTouristById(Long id);

    List<Tourist> findAll();

    List<Tourist> findAllTouristsByName(String name);

    void save(Tourist tourist);

    void delete(Tourist tourist);

    void deleteById(Long id);

    void updateTouristById(Long id, Tourist updatedTourist);
}
