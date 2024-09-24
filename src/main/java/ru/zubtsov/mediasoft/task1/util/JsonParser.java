package ru.zubtsov.mediasoft.task1.util;

import ru.zubtsov.mediasoft.task1.service.FlightService;
import ru.zubtsov.mediasoft.task1.model.CrewRole;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParser {

    private static final FlightService flightService = ClassManager.getFlightService();


    public static void parseFlights(String jsonString) {

        Pattern flightPattern = Pattern.compile(getFlightsRegex(), Pattern.DOTALL);
        Matcher flightMatcher = flightPattern.matcher(jsonString);

        while (flightMatcher.find()) {
            String planeType = flightMatcher.group(1);
            String flightNumber = flightMatcher.group(2);
            LocalDateTime departureTime = LocalDateTime.parse(flightMatcher.group(3));
            LocalDateTime arrivalTime = LocalDateTime.parse(flightMatcher.group(4));
            String departureAirport = flightMatcher.group(5);
            String arrivalAirport = flightMatcher.group(6);

            String crewString = flightMatcher.group(7);

            Pattern crewPattern = Pattern.compile(getCrewRegex());
            Matcher crewMatcher = crewPattern.matcher(crewString);

            flightService.createFlight(planeType, flightNumber, departureTime,
                    arrivalTime, departureAirport, arrivalAirport);

            while (crewMatcher.find()) {
                long memberId = Long.parseLong(crewMatcher.group(1));
                String name = crewMatcher.group(2);
                CrewRole role = CrewRole.fromString(crewMatcher.group(3));
                flightService.createCrewMember(flightNumber, memberId, name, role);
            }
        }
    }

    private static String getFlightsRegex() {
        return "\\{\\s*\"planeType\"\\s*:\\s*\"([^\"]*)\"," +
                "\\s*\"flightNumber\"\\s*:\\s*\"([^\"]*)\"," +
                "\\s*\"departureTime\"\\s*:\\s*\"([^\"]*)\"," +
                "\\s*\"arrivalTime\"\\s*:\\s*\"([^\"]*)\"," +
                "\\s*\"departureAirport\"\\s*:\\s*\"([^\"]*)\"," +
                "\\s*\"arrivalAirport\"\\s*:\\s*\"([^\"]*)\",\\s*\"crew\"\\s*:\\s*\\[(.*?)\\]\\s*\\}";
    }

    private static String getCrewRegex() {
        return "\\{\\s*\"memberId\"\\s*:\\s*\"([^\"]*)\"," +
                "\\s*\"name\"\\s*:\\s*\"([^\"]*)\"," +
                "\\s*\"role\"\\s*:\\s*\"([^\"]*)\"\\s*\\}";
    }
}
