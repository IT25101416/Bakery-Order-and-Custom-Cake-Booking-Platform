package com.example.bakeryproject.admin;

import com.example.bakeryproject.common.User;

public class Admin extends User {
    private String permissionLevel;

    public Admin() {
    }

    public Admin(String id, String name, String email, String password,
                 String permissionLevel) {
        super(id, name, email, password);
        this.permissionLevel = permissionLevel;
    }

    public String toFileString() {
        return getId() + "," + getName() + "," + getEmail() + "," +
                getPassword() + "," + permissionLevel;
    }

    public static Admin fromFileString(String line) {
        String[] data = line.split(",");
        return new Admin(data[0], data[1], data[2], data[3], data[4]);
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
}


