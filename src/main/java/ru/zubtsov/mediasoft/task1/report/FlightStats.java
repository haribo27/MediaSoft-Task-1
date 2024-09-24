package ru.zubtsov.mediasoft.task1.report;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Map;

public class FlightStats {
    private final int year; // Добавлено поле года
    private Duration totalDuration = Duration.ZERO;
    private final Map<LocalDate, Duration> dailyFlightTimes = new HashMap<>();
    private final Map<Integer, Duration> weeklyFlightTimes = new HashMap<>(); // неделя года
    private final Map<Integer, Duration> monthlyFlightTimes = new HashMap<>(); // месяц года

    // Конструктор с параметром года
    public FlightStats(int year) {
        this.year = year;
    }

    public void addFlightDuration(LocalDate flightDate, Duration flightDuration) {
        totalDuration = totalDuration.plus(flightDuration);

        // Учет дневного времени
        dailyFlightTimes.put(flightDate, dailyFlightTimes.getOrDefault(flightDate, Duration.ZERO).plus(flightDuration));

        // Учет недельного времени
        int weekOfYear = flightDate.get(WeekFields.of(DayOfWeek.MONDAY, 1).weekOfYear());
        weeklyFlightTimes.put(weekOfYear, weeklyFlightTimes.getOrDefault(weekOfYear, Duration.ZERO).plus(flightDuration));

        // Учет месячного времени
        int month = flightDate.getMonthValue();
        monthlyFlightTimes.put(month, monthlyFlightTimes.getOrDefault(month, Duration.ZERO).plus(flightDuration));
    }

    public Map<LocalDate, Duration> getDailyFlightTimes() {
        return dailyFlightTimes;
    }

    public Map<Integer, Duration> getWeeklyFlightTimes() {
        return weeklyFlightTimes;
    }

    public Map<Integer, Duration> getMonthlyFlightTimes() {
        return monthlyFlightTimes;
    }

    public int getYear() {
        return year; // Метод для получения года
    }
}



