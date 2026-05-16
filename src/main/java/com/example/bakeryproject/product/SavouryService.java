package com.example.bakeryproject.product;

import com.example.bakeryproject.common.FileUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SavouryService {

    private final String FILE_PATH = "src/main/resources/data/savouries.txt";

    public void addSavoury(Savoury savoury) {
        if (savoury.getSavouryId() == null || savoury.getSavouryId().trim().isEmpty() || "null".equalsIgnoreCase(savoury.getSavouryId())) {
            savoury.setSavouryId(generateSavouryId());
        }
        FileUtil.saveLine(FILE_PATH, savoury.toFileString());
    }

    public List<Savoury> getAllSavouries() {
        List<String> lines = FileUtil.readLines(FILE_PATH);
        List<Savoury> savouries = new ArrayList<>();

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                savouries.add(Savoury.fromFileString(line));
            }
        }

        return savouries;
    }

    public Savoury findSavouryById(String id) {
        if (id == null) {
            return null;
        }
        for (Savoury savoury : getAllSavouries()) {
            if (savoury.getSavouryId() != null && savoury.getSavouryId().equalsIgnoreCase(id.trim())) {
                return savoury;
            }
        }
        return null;
    }

    public Savoury findSavouryByName(String name) {
        if (name == null) {
            return null;
        }
        for (Savoury savoury : getAllSavouries()) {
            if (savoury.getSavouryName() != null && savoury.getSavouryName().equalsIgnoreCase(name.trim())) {
                return savoury;
            }
        }
        return null;
    }

    public void updateSavoury(Savoury updatedSavoury) {
        List<Savoury> savouries = getAllSavouries();
        List<String> updatedLines = new ArrayList<>();

        for (Savoury savoury : savouries) {
            if (savoury.getSavouryId().equals(updatedSavoury.getSavouryId())) {
                updatedLines.add(updatedSavoury.toFileString());
            } else {
                updatedLines.add(savoury.toFileString());
            }
        }

        FileUtil.overwriteFile(FILE_PATH, updatedLines);
    }

    public void deleteSavoury(String id) {
        List<Savoury> savouries = getAllSavouries();
        List<String> updatedLines = new ArrayList<>();

        for (Savoury savoury : savouries) {
            if (!savoury.getSavouryId().equals(id)) {
                updatedLines.add(savoury.toFileString());
            }
        }

        FileUtil.overwriteFile(FILE_PATH, updatedLines);
    }

    private String generateSavouryId() {
        int maxNumber = 0;
        for (Savoury savoury : getAllSavouries()) {
            String id = savoury.getSavouryId();
            if (id != null && id.toUpperCase().startsWith("S")) {
                try {
                    int number = Integer.parseInt(id.substring(1));
                    if (number > maxNumber) {
                        maxNumber = number;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return String.format("S%03d", maxNumber + 1);
    }
}
