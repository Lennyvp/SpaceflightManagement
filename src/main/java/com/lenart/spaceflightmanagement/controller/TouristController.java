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
public class TouristController {

    private TouristDao touristDao;
    private FlightDao flightDao;

    private TouristService touristService;
    private FlightService flightService;

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

    @DeleteMapping(value = "/tourists/del/{tourist_id}]")
    public void deleteTourist(@PathVariable("tourist_id") int tourist_id) {
        touristDao.deleteById((long) tourist_id);
    }

    @PutMapping(value = "/tourists/{tourist_id}/addflight/{flight_id}")
    public void updateTouristaddFlight(@PathVariable("tourist_id") int tourist_id, @PathVariable("flight_id") int flight_id) {
        List<Flight> flights = flightDao.findAllByCountOfSeatsGreaterThan(1);
        if (flights.size() > 0) {
            Flight flight = flightDao.findFlightById((long) flight_id);
            Tourist tourist = touristDao.findTouristById((long) tourist_id);
            tourist.addFlightToList(flight);
            touristDao.save(tourist);
            flightDao.save(flight);
        }
    }

    @PutMapping(value = "/tourists/{tourist_id}/delflight/{flight_id}")
    public void updateTouristdelFlight(@PathVariable("tourist_id") int tourist_id, @PathVariable("flight_id") int flightId) {
        Tourist tourist = touristDao.findTouristById((long) tourist_id);
        tourist.removeFlightToList(flightId);
        touristDao.save(tourist);
    }
}
