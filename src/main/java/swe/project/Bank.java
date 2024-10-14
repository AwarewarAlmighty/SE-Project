package swe.project;

import main.java.swe.project.controllers.AdminController;
import main.java.swe.project.controllers.UserController;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class Bank {
    public static void main(String[] args) {
        System.out.println("Welcome to the Bank Console Application!");

        AdminController adminController = new AdminController();
        UserController userController = new UserController();

        Scanner loginOption = new Scanner(System.in); // Create a Scanner object
        System.out.println("Are you login as an user or admin?");

        int userType = Integer.parseInt(loginOption.nextLine()); // Read user input

        switch (userType) {
            case 1:
                adminController.run();
                break;
            case 2:
                userController.run();
                break;
            default:
                break;
        }
        loginOption.close();

    }
}
