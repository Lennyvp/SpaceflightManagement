package com.lenart.spaceflightmanagement;

import com.lenart.spaceflightmanagement.DAO.FlightDao;
import com.lenart.spaceflightmanagement.DAO.TouristDao;
import com.lenart.spaceflightmanagement.model.Flight;
import com.lenart.spaceflightmanagement.model.Tourist;
import com.lenart.spaceflightmanagement.model.typeTourist.CountryTouristType;
import com.lenart.spaceflightmanagement.model.typeTourist.GenderTouristType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataLoader implements ApplicationRunner {

    private FlightDao flightDAO;
    private TouristDao touristDAO;

    @Autowired
    public DataLoader(FlightDao flightDAO, TouristDao touristDAO) {
        this.flightDAO = flightDAO;
        this.touristDAO = touristDAO;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Tourist tourist1 = new Tourist("Jan","Nowak", GenderTouristType.MAN, CountryTouristType.AUSTRALIA,"smoking",
                LocalDate.of(1994,10,10));
        Tourist tourist2 = new Tourist("Pjoter","Nowak", GenderTouristType.MAN,CountryTouristType.POLAND,"smoking",
                LocalDate.of(1994,10,10));
        Tourist tourist3 = new Tourist("Michal","Nowak", GenderTouristType.MAN,CountryTouristType.ENGLAND,"smoking",
                LocalDate.of(1994,10,10));


        Flight flight1 = new Flight(LocalDateTime.of(2018,11,11,12,23),
                LocalDateTime.of(2018,12,12,13,23),
                100,22.0);
        Flight flight2 = new Flight(LocalDateTime.of(2017,11,11,12,23),
                LocalDateTime.of(2018,12,12,13,23),
                150,23.0);

        Flight flight3 = new Flight(LocalDateTime.of(2218,3,4,5,6),
                LocalDateTime.of(1992,2,22,3,6),
                100,123.0);
        Flight flight4 = new Flight(LocalDateTime.of(2112,1,11,13,57),
                LocalDateTime.of(1410,9,12,14,12),
                1250,223.0);

        flightDAO.save(flight1);
        flightDAO.save(flight2);

        touristDAO.save(tourist1);

        flight1.addTouristToList(tourist1);
        flight2.addTouristToList(tourist1);

        tourist1.addFlightToList(flight1);
        tourist1.addFlightToList(flight2);

        flightDAO.save(flight1);
        flightDAO.save(flight2);
        touristDAO.save(tourist1);

        touristDAO.save(tourist2);
        touristDAO.save(tourist3);

        flightDAO.save(flight3);
        flightDAO.save(flight4);
    }
}
