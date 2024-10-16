package main.java.swe.project.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

import main.java.swe.project.models.User;

public class UserController {

    private List<User> users;
    private User currentUser;

    public UserController() {
        loadUsers();
    }

    // Load users from JSON file
    private void loadUsers() {
        try (FileReader reader = new FileReader("src\\main\\java\\swe\\project\\data\\users.json")) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<User>>(){}.getType();
            users = gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }

    // Save users back to JSON file
    private void saveUsers() {
        try (FileWriter writer = new FileWriter("src\\main\\java\\swe\\project\\data\\users.json")) {
            Gson gson = new Gson();
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // Find user by name
    private User findUserByName(String name) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

    // User login based on name and PIN
    public boolean login(String name, int pin) {
        currentUser = findUserByName(name);
        if (currentUser != null && currentUser.getPin() == pin) {
            System.out.println("Login successful! Welcome, " + currentUser.getName());
            return true;
        } else if (currentUser != null) {
            System.out.println("Invalid PIN.");
        } else {
            System.out.println("User not found.");
        }
        return false;
    }

    // View current user's balance
    public void viewBalance() {
        if (currentUser != null) {
            System.out.println("Current balance: $" + currentUser.getBalance());
        } else {
            System.out.println("No user is logged in.");
        }
    }

    // Deposit funds to the current user's balance
    public void depositFunds() {
        if (currentUser != null) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter the amount to deposit: ");
            double amount = input.nextDouble();
            if (amount > 0) {
                currentUser.setBalance(currentUser.getBalance() + amount);
                saveUsers();
                System.out.println("Deposited $" + amount + " successfully.");
            } else {
                System.out.println("Deposit amount must be positive.");
            }
        } else {
            System.out.println("No user is logged in.");
        }
    }

    // Withdraw funds from the current user's balance
    public void withdrawFunds() {
        if (currentUser != null) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter the amount to withdraw: ");
            double amount = input.nextDouble();
            if (amount > 0 && amount <= currentUser.getBalance()) {
                currentUser.setBalance(currentUser.getBalance() - amount);
                saveUsers();
                System.out.println("Withdrew $" + amount + " successfully.");
            } else if (amount > currentUser.getBalance()) {
                System.out.println("Insufficient balance for this withdrawal.");
            } else {
                System.out.println("Withdrawal amount must be positive.");
            }
        } else {
            System.out.println("No user is logged in.");
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        // Login step
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your PIN: ");
        int pin = scanner.nextInt();

        if (!login(name, pin)) {
            return;
        }

        // User menu after login
        int choice;
        do {
            System.out.println("\nUser Menu:");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewBalance();
                    break;
                case 2:
                    depositFunds();
                    break;
                case 3:
                    withdrawFunds();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 4);
    }
}
