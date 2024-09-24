package ru.zubtsov.mediasoft.task1.util;

import ru.zubtsov.mediasoft.task1.service.FlightReportService;
import ru.zubtsov.mediasoft.task1.service.FlightService;
import ru.zubtsov.mediasoft.task1.storage.FlightsStorage;

public class ClassManager {

    private static FlightService flightService;
    private static FlightsStorage flightsStorage;
    private static FlightReportService flightReportService;

    public static FlightService getFlightService() {
        if (flightService == null) {
            flightService = new FlightService();
            return flightService;
        } else {
            return flightService;
        }
    }

    public static FlightsStorage getFlightsStorage() {
        if (flightsStorage == null) {
            flightsStorage = new FlightsStorage();
            return flightsStorage;
        } else {
            return flightsStorage;
        }
    }

    public static FlightReportService getFlightReportService() {
        if (flightReportService == null) {
            flightReportService = new FlightReportService();
            return flightReportService;
        } else {
            return flightReportService;
        }
    }
}
