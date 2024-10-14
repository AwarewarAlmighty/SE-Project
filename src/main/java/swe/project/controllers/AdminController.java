package main.java.swe.project.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.reflect.Type;

import main.java.swe.project.models.User;

public class AdminController {

    private List<User> users;
    private String adminUsername;
    private String adminPassword;

    public AdminController() {
        LoadAdminCredentials();
        loadUsers();
    }

    private void loadUsers() {
        try (FileReader reader = new FileReader("../data/users.json")) {
          Gson gson = new Gson(); // Create a Gson instance

          // Read the entire JSON file into a String
          StringBuilder sb = new StringBuilder();
          int ch;
          while ((ch = reader.read()) != -1) {
              sb.append((char) ch);
          }
          String jsonString = sb.toString();
  
          // Use Gson to deserialize the JSON String into a List of User objects
          Type listType = new TypeToken<List<User>>(){}.getType();
          users = gson.fromJson(jsonString, listType);

        } catch (IOException e) {
            System.err.println("Error loading users from JSON file: " + e.getMessage());
        }
    }

    private void LoadAdminCredentials() {
        adminUsername = "admin";
        adminPassword = "password";
    }

    public void run() {
        Scanner loginScanner = new Scanner(System.in);
        
        
        System.out.print("Enter admin username: ");
        String username = loginScanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = loginScanner.nextLine();


        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            System.out.println("Welcome to the Admin Console Application!");
        } else {
            System.out.println("Invalid admin credentials. Access denied.");
        }

        loginScanner.close();
    }
}

