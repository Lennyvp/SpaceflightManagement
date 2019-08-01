package com.lenart.spaceflightmanagement.service;

import com.lenart.spaceflightmanagement.model.Flight;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {
    Flight findFlightById(Long id);

    List<Flight> findAll();

    List<Flight> findAllByIdLike(Long id);

    List<Flight> findAllByDateLike(LocalDateTime date);

    List<Flight> findAllByIntLike(int number);

    List<Flight> findAllByCountOfFreeSeatsGreaterThan(int amount);

    void save(Flight flight);

    void delete(Flight flight);

    void deleteById(Long id);

    void updateFlightById(Long id, Flight updatedFlight);
}
