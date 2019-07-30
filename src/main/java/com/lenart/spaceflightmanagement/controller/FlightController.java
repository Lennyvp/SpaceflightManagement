package com.lenart.spaceflightmanagement.controller;

import com.lenart.spaceflightmanagement.model.Flight;
import com.lenart.spaceflightmanagement.model.Tourist;
import com.lenart.spaceflightmanagement.service.FlightService;
import com.lenart.spaceflightmanagement.service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class FlightController {

    private TouristService touristService;
    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService, TouristService touristService) {
        this.flightService = flightService;
        this.touristService = touristService;
    }

    @GetMapping(value = "/api/flights")
    public List<Flight> getAllFlights() {
        return flightService.findAll();
    }

    @PostMapping(value = "/api/flights/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addFlight(@RequestBody Flight flight) {
        flight.getTouristSet().clear();
        flightService.save(flight);
    }

    @DeleteMapping(value = "/api/flights/del/{flight_id]")
    public void deleteFlight(@PathVariable("flight_id") int flight_id) {
        flightService.deleteById((long) flight_id);
    }

    @PutMapping(value = "/api/flights/{flight_id}/addtourist/{tourist_id}")
    public void updateTouristAddFlight(@PathVariable("flight_id") int flight_id, @PathVariable("tourist_id") int tourist_id) {
        Flight flight = flightService.findFlightById((long) flight_id);
        Tourist tourist = touristService.findTouristById((long) tourist_id);
        flight.addTouristToList(tourist);
        touristService.save(tourist);
        flightService.save(flight);
    }

    @PutMapping(value = "/api/flights/{flight_id}/deltourist/{tourist_id}")
    public void updateTouristDelFlight(@PathVariable("flight_id") int flight_id, @PathVariable("tourist_id") int tourist_id) {
        Flight flight = flightService.findFlightById((long) flight_id);
        flight.removeTouristFromList(tourist_id);
        flightService.save(flight);
    }

}
