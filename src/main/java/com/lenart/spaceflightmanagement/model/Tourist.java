package com.lenart.spaceflightmanagement.model;

import com.lenart.spaceflightmanagement.model.typeTourist.CountryTouristType;
import com.lenart.spaceflightmanagement.model.typeTourist.GenderTouristType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tourist {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private GenderTouristType gender;
    @NotNull
    private CountryTouristType country;
    @NotNull
    private String notes;
    @NotNull
    private LocalDate dateOfBirth;

    @ManyToMany
    @JoinColumn(name = "flight_id")
    private Set<Flight> flightList;

    public Tourist() {
    }

    public Tourist(String firstName, String lastName, GenderTouristType gender, CountryTouristType country, String notes, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.country = country;
        this.notes = notes;
        this.dateOfBirth = dateOfBirth;
        this.flightList = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GenderTouristType getGender() {
        return gender;
    }

    public void setGender(GenderTouristType gender) {
        this.gender = gender;
    }

    public CountryTouristType getCountry() {
        return country;
    }

    public void setCountry(CountryTouristType country) {
        this.country = country;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Flight> getFlightList() {
        return flightList;
    }

    public void setFlightList(Set<Flight> flightList) {
        this.flightList = flightList;
    }

    public void addFlightToList(Flight flight){
        flightList.add(flight);
    }

    public void removeFlightToList(Integer id){
        flightList.remove(id);
    }

    @Override
    public String toString() {
        return "Tourist{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", country='" + country + '\'' +
                ", notes='" + notes + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", flightList=" + flightList +
                '}';
    }
}
