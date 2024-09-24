package ru.zubtsov.mediasoft.task1.util;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonLoader {

    public static String loadJsonFile(String filePath) {
        String data = null;
        try {
            data = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public static void writeJsonToFile(String json, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
