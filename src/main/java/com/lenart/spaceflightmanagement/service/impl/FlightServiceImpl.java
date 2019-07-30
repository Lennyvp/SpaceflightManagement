package com.lenart.spaceflightmanagement.service.impl;

import com.lenart.spaceflightmanagement.dao.FlightDao;
import com.lenart.spaceflightmanagement.model.Flight;
import com.lenart.spaceflightmanagement.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private FlightDao flightDao;

    @Autowired
    public FlightServiceImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
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
        flightDao.delete(flight);
    }

    @Override
    public void deleteById(Long id) {
        flightDao.deleteById(id);
    }
}
