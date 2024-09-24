package ru.zubtsov.mediasoft.task1;


import ru.zubtsov.mediasoft.task1.report.CrewMemberReport;
import ru.zubtsov.mediasoft.task1.service.FlightReportService;
import ru.zubtsov.mediasoft.task1.service.FlightService;
import ru.zubtsov.mediasoft.task1.util.ClassManager;
import ru.zubtsov.mediasoft.task1.util.JsonLoader;
import ru.zubtsov.mediasoft.task1.util.JsonParser;

import java.io.IOException;
import java.util.List;

public class Main {

    private final static String FLIGHTS_DATA_PATH = "src/main/resources/flights.json";
    private final static String FLIGHT_REPORT_PATH = "src/main/resources/flightsReport.json";

    public static void main(String[] args) {
        try {
            processFlightData();
        } catch (Exception e) {
            System.err.println("An error occurred while processing flight data: " + e.getMessage());
        }
    }

    private static void processFlightData() throws IOException {
        // 1. Загрузка сервисов
        FlightService flightService = ClassManager.getFlightService();
        FlightReportService flightReportService = ClassManager.getFlightReportService();

        // 2. Чтение данных рейсов
        String flightsJson = JsonLoader.loadJsonFile(FLIGHTS_DATA_PATH);
        if (flightsJson == null || flightsJson.isEmpty()) {
            throw new IOException("Flight data file is empty or not found.");
        }
        JsonParser.parseFlights(flightsJson);

        // 3. Генерация отчетов
        List<CrewMemberReport> reports = flightReportService.generateReports(flightService.getAllFlights());

        // 4. Генерация JSON отчета и запись в файл
        String reportJson = CrewMemberReport.generateJsonReport(reports);
        JsonLoader.writeJsonToFile(reportJson, FLIGHT_REPORT_PATH);
    }
}