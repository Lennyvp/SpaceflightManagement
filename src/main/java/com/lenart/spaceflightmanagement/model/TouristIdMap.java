package com.lenart.spaceflightmanagement.model;

import javax.persistence.*;
import java.util.*;

//@Entity
public class TouristIdMap {

//    @Id
//    @GeneratedValue
//    private Long id;

//    @OneToOne
    private Flight flight;
    private Set<Long> touristIdSet;

    public TouristIdMap() {
    }

    public TouristIdMap(Flight flight, Set<Tourist> touristSet) {
        this.flight = flight;
        touristIdSet = new HashSet<>();
        addValues(touristSet);
    }

    public void addValue(Long touristId) {
        touristIdSet.add(touristId);
    }

    public void removeValue(Long touristId) {
        touristIdSet.remove(touristId);
    }

    public void addValues(Set<Tourist> touristList) {
        touristList.forEach(tourist -> touristIdSet.add(tourist.getId()));
    }

    public void removeValues(Set<Tourist> touristList) {
        touristList.forEach(tourist -> touristIdSet.remove(tourist.getId()));
    }

    public void setValues(Set<Tourist> touristList) {
        touristIdSet.clear();
        addValues(touristList);
    }

    public void removeAll() {
        touristIdSet.clear();
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Flight getFlight() {
//        return flight;
//    }
//
//    public void setFlight(Flight flight) {
//        this.flight = flight;
//    }
//
//    public Set<Long> getTouristIdSet() {
//        return touristIdSet;
//    }
//
//    public void setTouristIdSet(Set<Long> touristIdSet) {
//        this.touristIdSet = touristIdSet;
//    }
}
