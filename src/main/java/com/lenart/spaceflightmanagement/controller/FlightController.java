package com.lenart.spaceflightmanagement.controller;

import com.lenart.spaceflightmanagement.DAO.FlightDao;
import com.lenart.spaceflightmanagement.DAO.TouristDao;
import com.lenart.spaceflightmanagement.model.Flight;
import com.lenart.spaceflightmanagement.model.Tourist;
import com.lenart.spaceflightmanagement.service.FlightService;
import com.lenart.spaceflightmanagement.service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FlightController {

    private FlightDao flightDao;
    private TouristDao touristDao;

    private TouristService touristService;
    private FlightService flightService;

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

    @DeleteMapping(value = "/flights/del/{flight_id]")
    public void deleteFlight(@PathVariable("flight_id") int flight_id){
        flightDao.deleteById((long) flight_id);
    }

    @PutMapping(value = "/flights/{flight_id}/addtourist/{tourist_id}")
    public void updateTouristaddFlight(@PathVariable("flight_id") int flight_id, @PathVariable("tourist_id") int tourist_id) {
        List<Flight> flights = flightDao.findAllByCountOfSeatsGreaterThan(1);
        if (flights.size() > 0) {
            Flight flight = flightDao.findFlightById((long) flight_id);
            Tourist tourist = touristDao.findTouristById((long) tourist_id);
            flight.addTouristToList(tourist);
            touristDao.save(tourist);
            flightDao.save(flight);
        }
    }

    @PutMapping(value = "/flights/{flight_id}/deltourist/{tourist_id}")
    public void updateTouristdelFlight(@PathVariable("flight_id") int flight_id, @PathVariable("tourist_id") int tourist_id) {
        Flight flight = flightDao.findFlightById((long) flight_id);
        flight.removeTouristToList(tourist_id);
        flightDao.save(flight);
    }

}
