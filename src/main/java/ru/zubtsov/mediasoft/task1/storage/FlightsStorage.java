package ru.zubtsov.mediasoft.task1.storage;

import ru.zubtsov.mediasoft.task1.model.CrewMember;
import ru.zubtsov.mediasoft.task1.model.Flight;

import java.util.*;

public class FlightsStorage {

    private final HashMap<String, Flight> flights;
    private final Map<Long, CrewMember> crewMembers;

    public FlightsStorage() {
        this.flights = new HashMap<>();
        this.crewMembers = new HashMap<>();
    }

    public List<Flight> getFlights() {
        return new ArrayList<>(flights.values());
    }

    public void saveFlight(Flight flight) {
        flights.put(flight.getFlightNumber(), flight);
    }

    public Flight getFlightByFlightNumber(String flightNumber) {
        return flights.get(flightNumber);
    }

    public void saveCrewMember(CrewMember crewMember) {
        crewMembers.put(crewMember.getId(), crewMember);
    }

    public Optional<CrewMember> getCrewMember(long memberId) {
        return Optional.ofNullable(crewMembers.get(memberId));
    }
}
