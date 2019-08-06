package com.lenart.spaceflightmanagement.service.impl;

import com.lenart.spaceflightmanagement.dao.FlightDao;
import com.lenart.spaceflightmanagement.model.Flight;
import com.lenart.spaceflightmanagement.model.Tourist;
import com.lenart.spaceflightmanagement.service.FlightService;
import com.lenart.spaceflightmanagement.service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private FlightDao flightDao;
    private TouristService touristService;

    @Autowired
    public FlightServiceImpl(FlightDao flightDao, TouristService touristService) {
        this.flightDao = flightDao;
        this.touristService = touristService;
    }

    @Override
    public Flight findFlightById(Long id) {
        return flightDao.findFlightById(id);
    }

    @Override
    public List<Flight> findAll() {
        return flightDao.findAll();
    }

    @Override
    public List<Flight> findAllByIdLike(Long id) {
        return flightDao.findAllByIdLike(id);
    }

    @Override
    public List<Flight> findAllByDateLike(LocalDateTime date) {
        return flightDao.findAllByArrivalDateOrDepartureDateLike(date, date);
    }

    @Override
    public List<Flight> findAllByIntLike(int number) {
        return flightDao.findAllByCountOfFreeSeatsLikeOrCostOfTicketLike(number, number);
    }

    @Override
    public List<Flight> findAllByCountOfFreeSeatsGreaterThan(int amount) {
        return flightDao.findAllByCountOfFreeSeatsGreaterThan(amount);
    }

    @Override
    public void save(Flight flight) {
        flightDao.save(flight);
    }

    @Override
    public void delete(Flight flight) {
        deleteAllTourists(flight);
        flightDao.delete(flight);
    }

    @Override
    public void deleteById(Long id) {
        Flight flight  = flightDao.findFlightById(id);
        deleteAllTourists(flight);
        flightDao.deleteById(id);
    }

    @Override
    public void updateFlightById(Long id, Flight updatedFlight) {
        Flight flight = flightDao.findFlightById(id);

        flight.setId(id);
        flight.setDepartureDate( updatedFlight.getDepartureDate());
        flight.setArrivalDate( updatedFlight.getArrivalDate());
        flight.setCountOfFreeSeats( updatedFlight.getCountOfFreeSeats());
        flight.setCostOfTicket( updatedFlight.getCostOfTicket());

        flightDao.save(flight);
    }

    @Override
    public void addTouristToFlight(Long flightId, Long touristId) {
        touristService.addFlightToTourist(touristId, flightId);
    }

    @Override
    public void removeTouristFromFlight(Long flightId, Long touristId) {
        touristService.removeFlightFromTourist(touristId, flightId);
    }

    private void deleteAllTourists(Flight flight){
        flight.getTouristSet().forEach(tourist -> tourist.removeFlightFromList(flight.getId().intValue()));
    }
}
