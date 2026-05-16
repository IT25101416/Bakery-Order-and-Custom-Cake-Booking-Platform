package com.example.bakeryproject.C3_Order;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Component 03: Order Management
 *
 * FILE HANDLING: This utility class handles all file read/write operations
 * for orders.txt. It is shared across all components.
 *
 * Demonstrates:
 *  - Writing new records (Create)
 *  - Reading all records (Read)
 *  - Overwriting the file with updated content (Update / Delete)
 */
public class FileHandler {

    // WRITE - Append a new line to a file (Create)

    public static void saveToFile(String filename, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(data);
            writer.newLine();
            System.out.println("[FileHandler] Saved to " + filename + ": " + data);
        } catch (IOException e) {
            System.err.println("[FileHandler] Error writing to file: " + e.getMessage());
        }
    }

    // READ - Read all lines from a file (Read)

    public static List<String> readFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("[FileHandler] File not found: " + filename);
            return lines; // Return empty list if file does not exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // Skip blank lines
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("[FileHandler] Error reading file: " + e.getMessage());
        }

        return lines;
    }

    // OVERWRITE - Replace entire file content (used for Update & Delete)

    public static void overwriteFile(String filename, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("[FileHandler] File overwritten: " + filename);
        } catch (IOException e) {
            System.err.println("[FileHandler] Error overwriting file: " + e.getMessage());
        }
    }

    // SEARCH - Find a line by a keyword/ID in a file

    public static String findLineById(String filename, String id) {
        List<String> lines = readFromFile(filename);
        for (String line : lines) {
            if (line.startsWith(id + ",")) {
                return line;
            }
        }
        return null; // Not found
    }


    // DELETE - Remove a specific line by Order ID

    public static boolean deleteLineById(String filename, String id) {
        List<String> lines    = readFromFile(filename);
        List<String> filtered = new ArrayList<>();
        boolean found = false;

        for (String line : lines) {
            if (line.startsWith(id + ",")) {
                found = true; // Skip this line (delete it)
            } else {
                filtered.add(line);
            }
        }

        if (found) {
            overwriteFile(filename, filtered);
        }
        return found;
    }

}

