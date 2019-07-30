package com.lenart.spaceflightmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
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
    private int countOfFreeSeats;
    //    @NotNull
//    private int countOfAllSeats;
    @NotNull
    private double costOfTicket;

////    @OneToOne
//    @OneToOne(mappedBy = "flight" , cascade = CascadeType.ALL)
////    @OneToMany(targetEntity=TouristIdMap.class, mappedBy="flight", fetch=FetchType.EAGER)
//    private TouristIdMap touristIdMap;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "tourist_id")
    private Set<Tourist> touristSet;

    public Flight() {
    }

    public Flight(LocalDateTime departureDate, LocalDateTime arrivalDate, int countOfSeats, double costOfTicket) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.costOfTicket = costOfTicket;
        this.touristSet = new HashSet<>();
//        this.countOfAllSeats = countOfSeats;
        this.countOfFreeSeats = countOfSeats - touristSet.size();
//        this.touristIdMap = new TouristIdMap(this, touristList);
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

    /*
    START OF SEAT LOGIC
     */

    public int getCountOfFreeSeats() {
        return countOfFreeSeats;
    }

    public void setCountOfFreeSeats(int countOfFreeSeats) {
        this.countOfFreeSeats = countOfFreeSeats;
    }

    //    public int getCountOfAllSeats() {
//        return countOfAllSeats;
//    }

//    public void setCountOfAllSeats(int countOfAllSeats) {
//        this.countOfAllSeats = countOfAllSeats;
//    }

    public void reserveSeatAfterAddingTourist() {
        countOfFreeSeats -= 1;
    }

    public void returnSeatAfterRemovingTourist() {
        countOfFreeSeats += 1;
    }

     /*
    START OF TOURIST LOGIC
     */

    public void setTouristSet(Set<Tourist> touristSet) {
        this.touristSet = touristSet;
//        this.touristIdMap.setValues(touristList);
    }

    public Set<Tourist> getTouristSet() {
        return touristSet;
    }

//    @JsonIgnore
//    private boolean isEnoughSeats() {
//        return countOfFreeSeats > 0 && countOfFreeSeats <= countOfAllSeats;
//    }

    @JsonIgnore
    private boolean isEnoughSeats() {
        return countOfFreeSeats > 0;
    }

    public void addTouristToList(Tourist tourist) {
        if (isEnoughSeats()) {
            boolean condition = false;
            for (Tourist t : touristSet) {
//                touristSet.stream().filter(t->!t.equals(tourist)).forEach( t->reserveSeatAfterAddingTourist());
                if(tourist.equals(t)){
                    condition = true;
                    break;
                }
            }
            if(!condition){
                touristSet.add(tourist);
                reserveSeatAfterAddingTourist();
            }


//            touristSet.forEach(t -> {
//                if(!t.equals(tourist)){
//                    reserveSeatAfterAddingTourist();
//                }
//            });
//            touristSet.add(tourist);
//            touristSet.stream().filter(t->!t.equals(tourist)).forEach( t->reserveSeatAfterAddingTourist());
//            reserveSeatAfterAddingTourist();
            //        touristIdMap.addValue(tourist.getId());
        }
    }

    public void removeTouristFromList(Integer id) {
        if (isEnoughSeats()) {
            Optional<Tourist> searchedTourist = touristSet.stream()
                    .filter(tourist -> tourist.getId().equals(id.longValue())).findFirst();
            searchedTourist.ifPresent(flight -> touristSet.remove(flight));

            returnSeatAfterRemovingTourist();
//        touristIdMap.removeValue((long) id);
        }
    }

    public double getCostOfTicket() {
        return costOfTicket;
    }

    public void setCostOfTicket(double costOfTicket) {
        this.costOfTicket = costOfTicket;
    }

//    /*
//    Start of Tourist ID Map
//     */
//
//    public Set<Long> getValuesFromTouristIdMap(){
//        return touristIdMap.getTouristIdSet();
//    }
//
//    @JsonIgnore
//    public TouristIdMap getTouristIdMap() {
//        return touristIdMap;
//    }
//
//    @JsonIgnore
//    public void setTouristIdMap(TouristIdMap touristIdMap) {
//        this.touristIdMap = touristIdMap;
//    }
//
//    /*
//    End of Tourist ID Map
//     */

    /*
    FOR GUI
     */
    @JsonIgnore
    public LocalDate getArrivalDateWithoutTime() {
        return arrivalDate.toLocalDate();
    }

    @JsonIgnore
    public void setArrivalDateWithoutTime(LocalDate arrivalDate) {
        this.arrivalDate = LocalDateTime.of(arrivalDate, getArrivalTime());
    }

    @JsonIgnore
    public LocalTime getArrivalTime() {
        return arrivalDate.toLocalTime();
    }

    @JsonIgnore
    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalDate = LocalDateTime.of(getArrivalDateWithoutTime(), arrivalTime);
    }

    @JsonIgnore
    public LocalDate getDepartureDateWithoutTime() {
        return departureDate.toLocalDate();
    }

    @JsonIgnore
    public void setDepartureDateWithoutTime(LocalDate departureDate) {
        this.departureDate = LocalDateTime.of(departureDate, getDepartureTime());
    }

    @JsonIgnore
    public LocalTime getDepartureTime() {
        return departureDate.toLocalTime();
    }

    @JsonIgnore
    public void setDepartureTime(LocalTime departureTime) {
        this.departureDate = LocalDateTime.of(getDepartureDateWithoutTime(), departureTime);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", countOfSeats=" + countOfFreeSeats +
                ", costOfTicket=" + costOfTicket +
                ", touristList=" + touristSet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return countOfFreeSeats == flight.countOfFreeSeats &&
                Double.compare(flight.costOfTicket, costOfTicket) == 0 &&
                Objects.equals(departureDate, flight.departureDate) &&
                Objects.equals(arrivalDate, flight.arrivalDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureDate, arrivalDate, countOfFreeSeats, costOfTicket);
    }
}
