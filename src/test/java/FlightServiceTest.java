import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.zubtsov.mediasoft.task1.model.CrewMember;
import ru.zubtsov.mediasoft.task1.model.CrewRole;
import ru.zubtsov.mediasoft.task1.model.Flight;
import ru.zubtsov.mediasoft.task1.report.FlightStats;
import ru.zubtsov.mediasoft.task1.service.FlightService;
import ru.zubtsov.mediasoft.task1.storage.FlightsStorage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FlightServiceTest {

    private FlightService flightService;
    private FlightsStorage flightsStorage;

    @BeforeEach
    void setUp() {
        flightsStorage = new FlightsStorage();
        flightService = new FlightService();
    }

    @Test
    void shouldCreateFlightSuccessfully() {

        String planeType = "Boeing 747";
        String flightNumber = "FL123";
        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime arrivalTime = departureTime.plusHours(2);
        String departureAirport = "JFK";
        String arrivalAirport = "LAX";


        flightService.createFlight(planeType, flightNumber, departureTime, arrivalTime, departureAirport, arrivalAirport);

        Optional<Flight> savedFlightOpt = Optional.ofNullable(flightService.getFlightByNumber(flightNumber));

        assertTrue(savedFlightOpt.isPresent());
        Flight savedFlight = savedFlightOpt.get();
        assertEquals(planeType, savedFlight.getPlaneType());
        assertEquals(flightNumber, savedFlight.getFlightNumber());
        assertEquals(departureTime, savedFlight.getDepartureTime());
        assertEquals(arrivalTime, savedFlight.getArrivalTime());
        assertEquals(departureAirport, savedFlight.getDepartureAirport());
        assertEquals(arrivalAirport, savedFlight.getArrivalAirport());
    }

    @Test
    void shouldAddCrewMemberToFlight() {

        String flightNumber = "FL123";
        CrewMember crewMember = new CrewMember(1L, "John Doe", CrewRole.PILOT);


        String planeType = "Boeing 747";
        String departureAirport = "JFK";
        String arrivalAirport = "LAX";
        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime arrivalTime = departureTime.plusHours(2);
        flightService.createFlight(planeType, flightNumber, departureTime,
                arrivalTime, departureAirport, arrivalAirport);

        flightService.addCrewMemberToFlight(crewMember, flightNumber);

        Flight updatedFlight = flightService.getFlightByNumber(flightNumber);

        assertNotNull(updatedFlight);
        assertTrue(updatedFlight.getCrewMember().contains(crewMember));
    }

    @Test
    void shouldCreateCrewMemberSuccessfullyWhenCrewMemberDoesNotExist() {

        String flightNumber = "FL123";
        long memberId = 1L;
        String name = "John Doe";
        CrewRole role = CrewRole.PILOT;

        String planeType = "Boeing 747";
        String departureAirport = "JFK";
        String arrivalAirport = "LAX";
        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime arrivalTime = departureTime.plusHours(2);
        flightService.createFlight(planeType, flightNumber, departureTime,
                arrivalTime, departureAirport, arrivalAirport);

        flightService.createCrewMember(flightNumber, memberId, name, role);

        CrewMember savedCrewMember = flightService.getCrewMemberById(memberId);

        assertNotNull(savedCrewMember);
        assertEquals(memberId, savedCrewMember.getId());
        assertEquals(name, savedCrewMember.getName());
        assertEquals(role, savedCrewMember.getRole());

        Flight updatedFlight = flightService.getFlightByNumber(flightNumber);
        assertNotNull(updatedFlight);
        assertTrue(updatedFlight.getCrewMember().contains(savedCrewMember));
    }

    @Test
    void shouldCalculateFlightHoursCorrectly() {

        CrewMember crewMember = new CrewMember(1L, "John Doe", CrewRole.PILOT);
        Set<CrewMember> crewMembers = Set.of(crewMember);

        LocalDateTime departureTime1 = LocalDateTime.of(2023, 9, 25, 10, 0);
        LocalDateTime arrivalTime1 = departureTime1.plusHours(2);

        Flight flight1 = new Flight("Boeing 747", "FL123", departureTime1, arrivalTime1,
                "JFK", "LAX");
        flight1.setCrewMember(crewMembers);
        flightsStorage.saveFlight(flight1);

        LocalDateTime departureTime2 = LocalDateTime.of(2023, 9, 26, 8, 0);
        LocalDateTime arrivalTime2 = departureTime2.plusHours(4);

        Flight flight2 = new Flight("Boeing 747", "FL456", departureTime2, arrivalTime2,
                "JFK", "SFO");
        flight2.setCrewMember(crewMembers);
        flightsStorage.saveFlight(flight2);

        List<Flight> flights = flightsStorage.getFlights();

        Map<CrewMember, FlightStats> flightStats = flightService.calculateFlightHours(flights);

        assertNotNull(flightStats);
        assertTrue(flightStats.containsKey(crewMember));

        FlightStats stats = flightStats.get(crewMember);
        assertEquals(Duration.ofHours(6), stats.getTotalDuration());
    }
}
