package com.example.bakeryproject.common;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileUtil {

    public static void saveLine(String filePath, String line) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            FileWriter writer = new FileWriter(file, true);
            writer.write(line + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readLines(String filePath) {
        try {
            File file = new File(filePath);

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void overwriteFile(String filePath, List<String> lines) {
        try {
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
