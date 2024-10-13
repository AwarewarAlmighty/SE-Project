package swe.project;

import main.java.swe.project.controllers.AdminController;
import main.java.swe.project.controllers.UserController;

/**
 * Hello world!
 *
 */
public class Bank {
    public static void main(String[] args) {
        System.out.println("Welcome to the Bank Console Application!");

        // Initialize controllers
        AdminController adminController = new AdminController();
        UserController userController = new UserController();

        // Run the controllers (Example)
        adminController.run();
        userController.run();
    }
}
