package com.example.bakeryproject.admin;


import com.example.bakeryproject.common.FileUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private final String FILE_PATH = "src/main/resources/data/admins.txt";

    public void addAdmin(Admin admin) {
        FileUtil.saveLine(FILE_PATH, admin.toFileString());
    }
    public boolean loginAdmin(String username, String password) {
        System.out.println("Typed username: " + username);
        System.out.println("Typed password: " + password);

        for (Admin admin : getAllAdmins()) {
            System.out.println("File email: " + admin.getEmail());
            System.out.println("File password: " + admin.getPassword());

            if (admin.getEmail().equals(username) &&
                    admin.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public List<Admin> getAllAdmins() {
        List<String> lines = FileUtil.readLines(FILE_PATH);
        List<Admin> admins = new ArrayList<>();

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                admins.add(Admin.fromFileString(line));
            }
        }

        return admins;
    }

    public Admin findAdminById(String id) {
        for (Admin admin : getAllAdmins()) {
            if (admin.getId().equals(id)) {
                return admin;
            }
        }

        return null;
    }

    public void updateAdmin(Admin updatedAdmin) {
        List<Admin> admins = getAllAdmins();
        List<String> updatedLines = new ArrayList<>();

        for (Admin admin : admins) {
            if (admin.getId().equals(updatedAdmin.getId())) {
                updatedLines.add(updatedAdmin.toFileString());
            } else {
                updatedLines.add(admin.toFileString());
            }
        }

        FileUtil.overwriteFile(FILE_PATH, updatedLines);
    }

    public void deleteAdmin(String id) {
        List<Admin> admins = getAllAdmins();
        List<String> updatedLines = new ArrayList<>();

        for (Admin admin : admins) {
            if (!admin.getId().equals(id)) {
                updatedLines.add(admin.toFileString());
            }
        }

        FileUtil.overwriteFile(FILE_PATH, updatedLines);
    }
}