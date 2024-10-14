package main.java.swe.project.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import main.java.swe.project.models.User;

public class AdminController {

    private List<User> users;
    private String adminUsername;
    private String adminPassword;
    private Scanner scanner;

    public AdminController() {
        LoadAdminCredentials();
        loadUsers();
        scanner = new Scanner(System.in);
    }

    private User findUserByUsername(String name) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

    //load user data
    private void loadUsers() {
        try (FileReader reader = new FileReader("src\\main\\java\\swe\\project\\data\\users.json")) {
            Gson gson = new Gson();
            StringBuilder sb = new StringBuilder();
            int ch;
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            String jsonString = sb.toString();
            Type listType = new TypeToken<List<User>>(){}.getType();
            users = gson.fromJson(jsonString, listType);
        } catch (IOException e) {
            System.err.println("Error loading users from JSON file: " + e.getMessage());
            users = new ArrayList<>();
        }
    }

    //saves user data
    private void saveUsers() {
        try (FileWriter writer = new FileWriter("src\\main\\java\\swe\\project\\data\\users.json")) {
            Gson gson = new Gson();
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    private void LoadAdminCredentials() {
        adminUsername = "admin";
        adminPassword = "123456";
    }

    public void run() {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            System.out.println("Welcome to the Admin Console Application!");
            adminMenu();
        } else {
            System.out.println("Invalid admin credentials. Access denied.");
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Create User");
            System.out.println("2. Read User");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. Load all User");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    readUser();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    loadUsers();
                case 6:
                    System.out.println("Exiting Admin Console...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //Create User
    private void createUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        User newUser = new User(username, balance);
        users.add(newUser);
        saveUsers();
        System.out.println("User created successfully!");
    }

    //Read User
    private void readUser() {
        System.out.print("Enter username to read: ");
        String username = scanner.nextLine();

        User user = findUserByUsername(username);
        if (user != null) {
            System.out.println("User details:");
            System.out.println("Username: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("PIN: " + user.getPin());
            System.out.println("Balance: " + user.getBalance());
        } else {
            System.out.println("User not found.");
        }
    }

    //Update User
    private void updateUser() {
        System.out.print("Enter username to update: ");
        String username = scanner.nextLine();

        User user = findUserByUsername(username);
        if (user != null) {
            System.out.println("Enter new details (press enter to keep current value):");

            System.out.print("New balance (" + user.getBalance() + "): ");
            String newBalanceStr = scanner.nextLine();
            if (!newBalanceStr.isEmpty()) {
                try {
                    double newBalance = Double.parseDouble(newBalanceStr);
                    user.setBalance(newBalance);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid balance input. Balance not updated.");
                }
            }

            saveUsers();
            System.out.println("User updated successfully!");
        } else {
            System.out.println("User not found.");
        }
    }

    //Delete User
    private void deleteUser() {
        System.out.print("Enter username to delete: ");
        String username = scanner.nextLine();

        User user = findUserByUsername(username);
        if (user != null) {
            users.remove(user);
            saveUsers();
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("User not found.");
        }
    }

    
}