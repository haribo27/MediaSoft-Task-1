package ru.zubtsov.mediasoft.task1.service;

import ru.zubtsov.mediasoft.task1.report.CrewMemberReport;
import ru.zubtsov.mediasoft.task1.report.FlightStats;
import ru.zubtsov.mediasoft.task1.model.CrewMember;
import ru.zubtsov.mediasoft.task1.model.Flight;
import ru.zubtsov.mediasoft.task1.util.ClassManager;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlightReportService {

    private final FlightService flightService = ClassManager.getFlightService();

    public List<CrewMemberReport> generateReports(List<Flight> flights) {
        Map<CrewMember, FlightStats> flightStatsMap = flightService.calculateFlightHours(flights);
        List<CrewMemberReport> reports = new ArrayList<>();

        for (Map.Entry<CrewMember, FlightStats> entry : flightStatsMap.entrySet()) {
            CrewMember crewMember = entry.getKey();
            FlightStats stats = entry.getValue();
            CrewMemberReport report = new CrewMemberReport(crewMember);

            for (Map.Entry<Integer, Duration> monthlyEntry : stats.getMonthlyFlightTimes().entrySet()) {
                int month = monthlyEntry.getKey();
                long hours = monthlyEntry.getValue().toHours();
                boolean exceeded80 = hours > 80;

                // Проверка превышения 36 часов только для недель в текущем месяце
                boolean exceeded36 = stats.getWeeklyFlightTimes().entrySet().stream()
                        .filter(entry1 -> {
                            // Получаем номер недели и дату начала недели
                            int week = entry1.getKey();
                            LocalDate startOfWeek = LocalDate.ofYearDay(stats.getYear(), (week - 1) * 7 + 1);
                            return startOfWeek.getMonthValue() == month; // Проверяем, соответствует ли неделя текущему месяцу
                        })
                        .anyMatch(duration -> duration.getValue().toHours() > 36);

                // Проверка превышения 8 часов по дням текущего месяца
                boolean exceeded8 = stats.getDailyFlightTimes().entrySet().stream()
                        .filter(dailyEntry -> dailyEntry.getKey().getMonthValue() == month)
                        .anyMatch(dailyDuration -> dailyDuration.getValue().toHours() > 8);

                report.addMonthlyData(month, hours, exceeded80, exceeded36, exceeded8);
            }
            reports.add(report);
        }
        return reports;
    }
}
