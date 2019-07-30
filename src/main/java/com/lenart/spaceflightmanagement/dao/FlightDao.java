package com.lenart.spaceflightmanagement.dao;

import com.lenart.spaceflightmanagement.model.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightDao extends CrudRepository<Flight, Long> {
    List<Flight> findAll();

    List<Flight> findAllByCountOfFreeSeatsGreaterThan(int amount);

    List<Flight> findAllByIdLike(Long id);

    List<Flight> findAllByArrivalDateOrDepartureDateLike(LocalDateTime arrivalDate, LocalDateTime departureDate);

    List<Flight> findAllByCountOfFreeSeatsLikeOrCostOfTicketLike(int seats, double ticketCost);

    Flight findFlightById(Long id);
}
