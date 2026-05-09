package com.example.bakeryproject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    // --- CREATE: Method to save data (Appends to the end of the file) ---
    public static void saveToFile(String fileName, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data);
            writer.newLine();
            System.out.println("Data successfully saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // --- READ: Method to read all data ---
    public static List<String> readFromFile(String fileName) {
        List<String> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return records;
    }

    // --- UPDATE: Method to modify a specific line ---
    public static void updateLine(String fileName, int lineNumber, String newData) {
        List<String> lines = readFromFile(fileName); // Grab all current lines

        // Ensure the line number actually exists to prevent crashes
        if (lineNumber >= 0 && lineNumber < lines.size()) {
            lines.set(lineNumber, newData); // Swap the old line for the new data

            // Rewrite the entire file with the updated list
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
                System.out.println("Line " + lineNumber + " successfully updated.");
            } catch (IOException e) {
                System.out.println("Error updating file: " + e.getMessage());
            }
        } else {
            System.out.println("Error: Line number " + lineNumber + " does not exist.");
        }
    }

    // --- DELETE: New Method to remove a specific line ---
    public static void deleteLine(String fileName, int lineNumber) {
        List<String> lines = readFromFile(fileName); // Grab all current lines

        // Ensure the line exists
        if (lineNumber >= 0 && lineNumber < lines.size()) {
            lines.remove(lineNumber); // Erase the line from the list

            // Rewrite the file without that line
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
                System.out.println("Line " + lineNumber + " successfully deleted.");
            } catch (IOException e) {
                System.out.println("Error deleting from file: " + e.getMessage());
            }
        } else {
            System.out.println("Error: Line number " + lineNumber + " does not exist.");
        }
    }
}