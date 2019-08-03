package com.lenart.spaceflightmanagement.model;

import com.fasterxml.jackson.annotation.*;
import com.lenart.spaceflightmanagement.model.typeTourist.CountryTouristType;
import com.lenart.spaceflightmanagement.model.typeTourist.GenderTouristType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
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

    private String notes;
    @NotNull
    private LocalDate dateOfBirth;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("touristSet")
    private Set<Flight> flightSet;

    public Tourist() {
    }

    public Tourist(String firstName, String lastName, GenderTouristType gender, CountryTouristType country, String notes, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.country = country;
        this.notes = notes;
        this.dateOfBirth = dateOfBirth;
        this.flightSet = new HashSet<>();
//        flightSet.forEach(Flight::reserveSeatAfterAddingTourist);
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

    public Set<Flight> getFlightSet() {
        return flightSet;
    }

    public void setFlightSet(Set<Flight> flightSet) {
        this.flightSet = flightSet;
    }

//    private boolean isEnoughSeats(Flight flight) {
//        return flight.getCountOfFreeSeats() > 0 && flight.getCountOfFreeSeats() <= flight.getCountOfAllSeats();
//    }

    private boolean isEnoughSeats(Flight flight) {
        return flight.getCountOfFreeSeats() > 0;
    }

    public void addFlightToList(Flight flight) {
        if (isEnoughSeats(flight)) {
            flightSet.add(flight);
//            flight.reserveSeatAfterAddingTourist();
        }
    }

    private Optional<Flight> getOptionalFlight() {
        return flightSet.stream()
                .filter(flight -> flight.getId().equals(id.longValue()))
                .findFirst();
    }

//    private void returnSeatAfterRemovingFlight() {
//        flightSet.stream()
//                .filter(flight -> flight.getId().equals(id.longValue()))
//                .filter(this::isEnoughSeats)
//                .forEach(Flight::returnSeatAfterRemovingTourist);
//    }

    public void removeFlightFromList(Integer id) {
        Optional<Flight> searchedFlight = getOptionalFlight();
        searchedFlight
                .filter(this::isEnoughSeats)
                .ifPresent(flight -> flightSet.remove(flight));
//        returnSeatAfterRemovingFlight();
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
                ", flightList=" + flightSet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tourist tourist = (Tourist) o;
        return Objects.equals(firstName, tourist.firstName) &&
                Objects.equals(lastName, tourist.lastName) &&
                gender == tourist.gender &&
                country == tourist.country &&
                Objects.equals(notes, tourist.notes) &&
                Objects.equals(dateOfBirth, tourist.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, gender, country, notes, dateOfBirth);
    }
}
