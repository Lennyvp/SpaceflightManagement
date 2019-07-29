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
public class FlightController {

    private FlightDao flightDao;
    private TouristDao touristDao;

    @Autowired
    public FlightController(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @GetMapping(value = "/flights")
    public List<Flight> getAllFlights(){
        return flightDao.findAll();
    }

    @PostMapping(value = "/flights/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addFlight(@RequestBody Flight flight){
        flightDao.save(flight);
    }

    @DeleteMapping(value = "/flights/del/{number]")
    public void deleteFlight(@PathVariable("number") int number){
        flightDao.deleteById((long) number);
    }

    @PutMapping(value = "/flights/{flight_id}/addtourist/{tourist_id}")
    public void updateTouristaddFlight(@PathVariable("flight_id") int flightId, @PathVariable("tourist_id") int touristId) {
        List<Flight> flights = flightDao.findAllByCountOfSeatsGreaterThan(1);
        if (flights.size() > 0) {
            Flight flight = flightDao.findFlightById((long) flightId);
            Tourist tourist = touristDao.findTouristById((long) touristId);
            flight.addTouristToList(tourist);
            touristDao.save(tourist);
            flightDao.save(flight);
        }
    }

    @PutMapping(value = "/flights/{flight_id}/deltourist/{tourist_id}")
    public void updateTouristdelFlight(@PathVariable("number") int number, @PathVariable("flight_id") int flightId) {
        Flight flight = flightDao.findFlightById((long) flightId);
        flight.removeTouristToList(flightId);
        flightDao.save(flight);
    }

}
