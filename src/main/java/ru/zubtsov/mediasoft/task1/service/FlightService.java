package ru.zubtsov.mediasoft.task1.service;

import ru.zubtsov.mediasoft.task1.model.CrewMember;
import ru.zubtsov.mediasoft.task1.model.CrewRole;
import ru.zubtsov.mediasoft.task1.model.Flight;
import ru.zubtsov.mediasoft.task1.report.FlightStats;
import ru.zubtsov.mediasoft.task1.storage.FlightsStorage;
import ru.zubtsov.mediasoft.task1.util.ClassManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FlightService {

    private final FlightsStorage flightsStorage = ClassManager.getFlightsStorage();

    public void createFlight(String planeType, String flightNumber,
                             LocalDateTime departureTime, LocalDateTime arrivalTime,
                             String departureAirport, String arrivalAirport) {

        Flight flight = new Flight(planeType, flightNumber, departureTime,
                arrivalTime, departureAirport, arrivalAirport);
        flightsStorage.saveFlight(flight);
    }

    public void createCrewMember(String flightNumber, long memberId,
                                 String name, CrewRole role) {
        Optional<CrewMember> maybeCrewMember = flightsStorage.getCrewMember(memberId);
        if (maybeCrewMember.isPresent()) {
            addCrewMemberToFlight(maybeCrewMember.get(), flightNumber);
            flightsStorage.saveCrewMember(maybeCrewMember.get());
        } else {
            CrewMember crewMember = new CrewMember(memberId, name, role);
            addCrewMemberToFlight(crewMember, flightNumber);
            flightsStorage.saveCrewMember(crewMember);
        }
    }

    public void addCrewMemberToFlight(CrewMember crewMember, String flightNumber) {
        flightsStorage.getFlightByFlightNumber(flightNumber)
                .getCrewMember()
                .add(crewMember);
    }

    public List<Flight> getAllFlights() {
        return flightsStorage.getFlights();
    }

    public Flight getFlightByNumber(String flightNumber) {
        return flightsStorage.getFlightByFlightNumber(flightNumber);
    }

    public CrewMember getCrewMemberById(long memberId) {
        return flightsStorage.getCrewMember(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Crew member does not found"));
    }

    public Map<CrewMember, FlightStats> calculateFlightHours(List<Flight> flights) {
        Map<CrewMember, FlightStats> flightStatsMap = new HashMap<>();

        for (Flight flight : flights) {
            Duration flightDuration = Duration.between(flight.getDepartureTime(), flight.getArrivalTime());
            int flightYear = flight.getDepartureTime().getYear();

            for (CrewMember crewMember : flight.getCrewMember()) {

                FlightStats stats = flightStatsMap.computeIfAbsent(crewMember, cm -> new FlightStats(flightYear));

                stats.addFlightDuration(flight.getDepartureTime().toLocalDate(), flightDuration);
            }
        }
        return flightStatsMap;
    }
}