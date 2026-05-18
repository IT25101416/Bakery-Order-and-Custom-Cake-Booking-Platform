package com.example.bakeryproject.customer;

import com.example.bakeryproject.common.FileUtil;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final String FILE_PATH = "src/main/resources/data/customers.txt";

    public void addCustomer(Customer customer) {
        if (customer.getId() == null || customer.getId().trim().isEmpty() || "null".equalsIgnoreCase(customer.getId().trim())) {
            customer.setId(generateNextCustomerId());
        }
        FileUtil.saveLine(FILE_PATH, customer.toFileString());
    }

    private String generateNextCustomerId() {
        int maxIdNumber = 0;
        for (Customer customer : getAllCustomers()) {
            String customerId = customer.getId();
            if (customerId != null && customerId.matches("C\\d+")) {
                int idNumber = Integer.parseInt(customerId.substring(1));
                if (idNumber > maxIdNumber) {
                    maxIdNumber = idNumber;
                }
            }
        }
        return String.format("C%03d", maxIdNumber + 1);
    }

    public void updateCustomer(Customer updatedCustomer) {
        List<Customer> customers = getAllCustomers();
        List<String> updatedLines = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getId().equals(updatedCustomer.getId())) {
                updatedLines.add(updatedCustomer.toFileString());
            } else {
                updatedLines.add(customer.toFileString());
            }
        }
        FileUtil.overwriteFile(FILE_PATH, updatedLines);
    }

    public Customer loginCustomer(String username, String password) {
        for (Customer customer : getAllCustomers()) {
            if (customer.getEmail().equals(username) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }

    public List<Customer> getAllCustomers() {
        List<String> lines = FileUtil.readLines(FILE_PATH);
        List<Customer> customers = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                customers.add(Customer.fromFileString(line));
            }
        }
        return customers;
    }

    public Customer findById(String id) {
        for (Customer customer : getAllCustomers()) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    public void deleteCustomer(String id) {
        List<Customer> customers = getAllCustomers();
        List<String> updatedLines = new ArrayList<>();
        for (Customer customer : customers) {
            if (!customer.getId().equals(id)) {
                updatedLines.add(customer.toFileString());
            }
        }
        FileUtil.overwriteFile(FILE_PATH, updatedLines);
    }
}