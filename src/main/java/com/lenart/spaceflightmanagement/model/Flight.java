package com.lenart.spaceflightmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Flight {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private LocalDateTime departureDate = LocalDateTime.now();
    @NotNull
    private LocalDateTime arrivalDate = LocalDateTime.now();
    @NotNull
    private int countOfSeats;
    @NotNull
    private double costOfTicket;

    @JsonIgnore
    @ManyToMany
    @JoinColumn(name = "tourist_id")
    private Set<Tourist> touristList;

    public Flight() {
    }

    public Flight(LocalDateTime departureDate, LocalDateTime arrivalDate, int countOfSeats, double costOfTicket) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.countOfSeats = countOfSeats;
        this.costOfTicket = costOfTicket;
        this.touristList = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getCountOfSeats() {
        return countOfSeats;
    }

    public void setCountOfSeats(int countOfSeats) {
        this.countOfSeats = countOfSeats;
    }

    public void setTouristList(Set<Tourist> touristList) {
        this.touristList = touristList;
    }

    public Set<Tourist> getTouristList() {
        return touristList;
    }

    public void addTouristToList(Tourist tourist) {
        touristList.add(tourist);
    }

    public void removeTouristToList(Integer id) {
        touristList.remove(id);
    }

    public double getCostOfTicket() {
        return costOfTicket;
    }

    public void setCostOfTicket(double costOfTicket) {
        this.costOfTicket = costOfTicket;
    }

    @JsonIgnore
    public LocalDate getArrivalDateWithoutTime(){
        return arrivalDate.toLocalDate();
    }
    @JsonIgnore
    public void setArrivalDateWithoutTime(LocalDate arrivalDate){
        this.arrivalDate = LocalDateTime.of(arrivalDate, getArrivalTime());
    }
    @JsonIgnore
    public LocalTime getArrivalTime(){
        return arrivalDate.toLocalTime();
    }

    @JsonIgnore
    public void setArrivalTime(LocalTime arrivalTime){
        this.arrivalDate = LocalDateTime.of(getArrivalDateWithoutTime(),arrivalTime);
    }

    @JsonIgnore
    public LocalDate getDepartureDateWithoutTime(){
        return departureDate.toLocalDate();
    }

    @JsonIgnore
    public void setDepartureDateWithoutTime(LocalDate departureDate){
        this.departureDate = LocalDateTime.of(departureDate, getDepartureTime());
    }

    @JsonIgnore
    public LocalTime getDepartureTime(){
        return departureDate.toLocalTime();
    }

    @JsonIgnore
    public void setDepartureTime(LocalTime departureTime){
        this.departureDate = LocalDateTime.of(getDepartureDateWithoutTime(),departureTime);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", countOfSeats=" + countOfSeats +
                ", costOfTicket=" + costOfTicket +
                ", touristList=" + touristList +
                '}';
    }
}
