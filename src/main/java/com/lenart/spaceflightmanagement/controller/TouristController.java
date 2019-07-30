package com.lenart.spaceflightmanagement.controller;

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

    private TouristService touristService;
    private FlightService flightService;

    @Autowired
    public TouristController(TouristService touristService, FlightService flightService) {
        this.touristService = touristService;
        this.flightService = flightService;
    }

    @GetMapping(value = "/tourists")
    public List<Tourist> getAllTourists() {
        return touristService.findAll();
    }

    @PostMapping(value = "/tourists/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addTourist(@RequestBody Tourist tourist) {
        tourist.getFlightSet().clear(); //cannot add list of flight in JSON
        touristService.save(tourist);
    }

    @DeleteMapping(value = "/tourists/del/{tourist_id}]")
    public void deleteTourist(@PathVariable("tourist_id") int tourist_id) {
        touristService.deleteById((long) tourist_id);
    }

    @PutMapping(value = "/tourists/{tourist_id}/addflight/{flight_id}")
    public void updateTouristAddFlight(@PathVariable("tourist_id") int tourist_id, @PathVariable("flight_id") int flight_id) {
        Flight flight = flightService.findFlightById((long) flight_id);
        Tourist tourist = touristService.findTouristById((long) tourist_id);
        tourist.addFlightToList(flight);
        touristService.save(tourist);
        flightService.save(flight);
    }

    @PutMapping(value = "/tourists/{tourist_id}/delflight/{flight_id}")
    public void updateTouristDelFlight(@PathVariable("tourist_id") int tourist_id, @PathVariable("flight_id") int flight_id) {
        Tourist tourist = touristService.findTouristById((long) tourist_id);
        tourist.removeFlightToList(flight_id);
        touristService.save(tourist);
    }
}
