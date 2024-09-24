package ru.zubtsov.mediasoft.task1.report;

import ru.zubtsov.mediasoft.task1.model.CrewMember;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewMemberReport {
    private final CrewMember crewMember;
    private final Map<Integer, MonthlyFlightData> monthlyData = new HashMap<>();

    public CrewMemberReport(CrewMember crewMember) {
        this.crewMember = crewMember;
    }

    public void addMonthlyData(int month, long hours, boolean exceeded80, boolean exceeded36, boolean exceeded8) {
        monthlyData.put(month, new MonthlyFlightData(month, hours, exceeded80, exceeded36, exceeded8));
    }

    public CrewMember getCrewMember() {
        return crewMember;
    }

    public Map<Integer, MonthlyFlightData> getMonthlyData() {
        return monthlyData;
    }

    public static String generateJsonReport(List<CrewMemberReport> reports) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");

        for (int i = 0; i < reports.size(); i++) {
            CrewMemberReport report = reports.get(i);
            jsonBuilder.append("  {\n");

            // Добавляем информацию о члене экипажа
            jsonBuilder.append("    \"crewMember\": {\n");
            jsonBuilder.append("      \"name\": \"").append(report.getCrewMember().getName()).append("\",\n");
            jsonBuilder.append("      \"role\": \"").append(report.getCrewMember().getRole()).append("\"\n");
            jsonBuilder.append("    },\n");

            // Добавляем данные по месяцам
            jsonBuilder.append("    \"monthlyData\": [\n");
            int monthlyDataSize = report.getMonthlyData().size();
            int j = 0;
            for (Map.Entry<Integer, MonthlyFlightData> entry : report.getMonthlyData().entrySet()) {
                MonthlyFlightData data = entry.getValue();
                jsonBuilder.append("      {\n");
                jsonBuilder.append("        \"month\": ").append(data.month()).append(",\n");
                jsonBuilder.append("        \"hours\": ").append(data.hours()).append(",\n");
                jsonBuilder.append("        \"exceeded80\": ").append(data.exceeded80()).append(",\n");
                jsonBuilder.append("        \"exceeded36\": ").append(data.exceeded36()).append(",\n");
                jsonBuilder.append("        \"exceeded8\": ").append(data.exceeded8()).append("\n");
                jsonBuilder.append("      }");

                if (j < monthlyDataSize - 1) {
                    jsonBuilder.append(",\n");
                }
                j++;
            }
            jsonBuilder.append("\n    ]\n");
            jsonBuilder.append("  }");

            if (i < reports.size() - 1) {
                jsonBuilder.append(",\n");
            }
        }

        jsonBuilder.append("\n]");
        return jsonBuilder.toString();
    }

    public record MonthlyFlightData(int month, long hours, boolean exceeded80,
                                    boolean exceeded36, boolean exceeded8) {
    }
}