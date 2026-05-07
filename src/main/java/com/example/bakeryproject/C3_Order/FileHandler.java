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


