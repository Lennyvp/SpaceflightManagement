package com.lenart.spaceflightmanagement.controller;

import com.lenart.spaceflightmanagement.DAO.FlightDao;
import com.lenart.spaceflightmanagement.DAO.TouristDao;
import com.lenart.spaceflightmanagement.model.Flight;
import com.lenart.spaceflightmanagement.model.Tourist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TouristController {

    private TouristDao touristDao;
    private FlightDao flightDao;

    @Autowired
    public TouristController(TouristDao touristDao, FlightDao flightDao) {
        this.touristDao = touristDao;
        this.flightDao = flightDao;
    }

    @GetMapping(value = "/tourists")
    public List<Tourist> getAllTourists() {
        return touristDao.findAll();
    }

    @PostMapping(value = "/tourists/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addTourist(@RequestBody Tourist tourist) {
        touristDao.save(tourist);
    }

    @DeleteMapping(value = "/tourists/del/{number]")
    public void deleteTourist(@PathVariable("number") int number) {
        touristDao.deleteById((long) number);
    }

    @PutMapping(value = "/tourists/{number}/addflight/{flight_id}")
    public void updateTouristaddFlight(@PathVariable("number") int number, @PathVariable("flight_id") int flightId) {
        List<Flight> flights = flightDao.findAllByCountOfSeatsGreaterThan(1);
        if (flights.size() > 0) {
            Flight flight = flightDao.findFlightById((long) flightId);
            Tourist tourist = touristDao.findTouristById((long) number);
            tourist.addFlightToList(flight);
            touristDao.save(tourist);
            flightDao.save(flight);
        }
    }

    @PutMapping(value = "/tourists/{number}/delflight/{flight_id}")
    public void updateTouristdelFlight(@PathVariable("number") int number, @PathVariable("flight_id") int flightId) {
        Tourist tourist = touristDao.findTouristById((long) number);
        tourist.removeFlightToList(flightId);
        touristDao.save(tourist);
    }
}
