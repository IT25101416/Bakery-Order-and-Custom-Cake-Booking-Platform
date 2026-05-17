package com.example.bakeryproject.product;



import com.example.bakeryproject.common.FileUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CakeService {

    private final String FILE_PATH = "src/main/resources/data/cakes.txt";

    public void addCake(Cake cake) {
        FileUtil.saveLine(FILE_PATH, cake.toFileString());
    }

    public List<Cake> getAllCakes() {
        List<String> lines = FileUtil.readLines(FILE_PATH);
        List<Cake> cakes = new ArrayList<>();

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                cakes.add(Cake.fromFileString(line));
            }
        }

        return cakes;
    }

    public Cake findCakeById(String id) {
        for (Cake cake : getAllCakes()) {
            if (cake.getCakeId().equals(id)) {
                return cake;
            }
        }
        return null;
    }

    public Cake findCakeByName(String cakeName) {
        if (cakeName == null) {
            return null;
        }

        for (Cake cake : getAllCakes()) {
            if (cake.getCakeName() != null &&
                    cake.getCakeName().equalsIgnoreCase(cakeName.trim())) {
                return cake;
            }
        }
        return null;
    }

    public void updateCake(Cake updatedCake) {
        List<Cake> cakes = getAllCakes();
        List<String> updatedLines = new ArrayList<>();

        for (Cake cake : cakes) {
            if (cake.getCakeId().equals(updatedCake.getCakeId())) {
                updatedLines.add(updatedCake.toFileString());
            } else {
                updatedLines.add(cake.toFileString());
            }
        }

        FileUtil.overwriteFile(FILE_PATH, updatedLines);
    }

    public void deleteCake(String id) {
        List<Cake> cakes = getAllCakes();
        List<String> updatedLines = new ArrayList<>();

        for (Cake cake : cakes) {
            if (!cake.getCakeId().equals(id)) {
                updatedLines.add(cake.toFileString());
            }
        }

        FileUtil.overwriteFile(FILE_PATH, updatedLines);
    }
}


