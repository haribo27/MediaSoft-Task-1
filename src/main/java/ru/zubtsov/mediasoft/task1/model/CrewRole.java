package ru.zubtsov.mediasoft.task1.model;

public enum CrewRole {
    PILOT,
    CO_PILOT,
    FLIGHT_ATTENDANT;

    // Метод для сопоставления строкового значения с Enum
    public static CrewRole fromString(String role) {
        return switch (role.toLowerCase()) {
            case "pilot" -> PILOT;
            case "co_pilot" -> CO_PILOT;
            case "flight_attendant" -> FLIGHT_ATTENDANT;
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
    }
}