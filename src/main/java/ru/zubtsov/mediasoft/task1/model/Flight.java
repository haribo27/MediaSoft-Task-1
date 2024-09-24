package ru.zubtsov.mediasoft.task1.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Flight {

    private final String planeType;
    private final String flightNumber;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;
    private final String departureAirport;
    private final String arrivalAirport;
    private Set<CrewMember> crewMember;

    public Flight(String planeType, String flightNumber, LocalDateTime departureTime,
                  LocalDateTime arrivalTime, String departureAirport,
                  String arrivalAirport) {
        this.planeType = planeType;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.crewMember = new HashSet<>();
    }

    public String getPlaneType() {
        return planeType;
    }

    public Set<CrewMember> getCrewMember() {
        return crewMember;
    }

    public void setFlightCrewMember(Set<CrewMember> crewMember) {
        this.crewMember = crewMember;
    }


    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(flightNumber, flight.flightNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(flightNumber);
    }
}
