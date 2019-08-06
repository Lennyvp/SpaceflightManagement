package com.lenart.spaceflightmanagement.service.impl;

import com.lenart.spaceflightmanagement.dao.FlightDao;
import com.lenart.spaceflightmanagement.dao.TouristDao;
import com.lenart.spaceflightmanagement.model.Flight;
import com.lenart.spaceflightmanagement.model.Tourist;
import com.lenart.spaceflightmanagement.service.FlightService;
import com.lenart.spaceflightmanagement.service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristServiceImpl implements TouristService {

    private TouristDao touristDao;
    private FlightDao flightDao;

    @Autowired
    public TouristServiceImpl(TouristDao touristDao, FlightDao flightDao) {
        this.touristDao = touristDao;
        this.flightDao = flightDao;
    }

    @Override
    public Tourist findTouristById(Long id) {
        return touristDao.findTouristById(id);
    }

    @Override
    public List<Tourist> findAll() {
        return touristDao.findAll();
    }

    @Override
    public List<Tourist> findAllTouristsByName(String name) {
        return touristDao.findAllByFirstNameLikeOrLastNameLike(name, name);
    }

    @Override
    public void save(Tourist tourist) {
        touristDao.save(tourist);
    }

    @Override
    public void delete(Tourist tourist) {
        deleteAllFlights(tourist);
        touristDao.delete(tourist);
    }

    @Override
    public void deleteById(Long id) {
        Tourist tourist = findTouristById(id);
        deleteAllFlights(tourist);
        touristDao.deleteById(id);
    }

    @Override
    public void updateTouristById(Long id, Tourist updatedTourist) {
        Tourist tourist = touristDao.findTouristById(id);

        tourist.setId(id);
        tourist.setFirstName(updatedTourist.getFirstName());
        tourist.setLastName(updatedTourist.getLastName());
        tourist.setGender(updatedTourist.getGender());
        tourist.setCountry(updatedTourist.getCountry());
        tourist.setNotes(updatedTourist.getNotes());
        tourist.setDateOfBirth(updatedTourist.getDateOfBirth());

        touristDao.save(tourist);
    }

    @Override
    public void addFlightToTourist(Long touristId, Long flightId) {
        Flight flight = flightDao.findFlightById(flightId);
        Tourist tourist = touristDao.findTouristById(touristId);
        tourist.addFlightToList(flight);
        flight.addTouristToList(tourist);
        touristDao.save(tourist);
        flightDao.save(flight);
    }

    @Override
    public void removeFlightFromTourist(Long touristId, Long flightId) {
        Flight flight = flightDao.findFlightById(flightId);
        Tourist tourist = touristDao.findTouristById(touristId);
        tourist.removeFlightFromList(flightId.intValue());
        flight.removeTouristFromList(touristId.intValue());
        touristDao.save(tourist);
        flightDao.save(flight);
    }

    private void deleteAllFlights(Tourist tourist){
        int touristId = tourist.getId().intValue();
        tourist.getFlightSet().forEach(flight -> flight.removeTouristFromList(touristId));
    }

}
