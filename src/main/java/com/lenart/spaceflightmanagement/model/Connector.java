package com.lenart.spaceflightmanagement.model;

public class Connector {

    private Long touristId;
    private Long flightId;

    public Connector(Long touristId, Long flightId) {
        this.touristId = touristId;
        this.flightId = flightId;
    }

    public Long getTouristId() {
        return touristId;
    }

    public void setTouristId(Long touristId) {
        this.touristId = touristId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
}
