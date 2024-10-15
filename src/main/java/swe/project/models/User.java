package main.java.swe.project.models;

public class User {
  private String name;
  private String email;
  private double balance;
  private int pin;

  public User(String name, double balance) {
    this.name = name;
    this.balance = balance;
  }

  public String getName() {
    return name;
  }

  public double getBalance() {
    return balance;
  }

  public int getPin() {
    return pin;
  }

  public String getEmail() {
    return email;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void setPin(int pin) {
    if (pin >= 100000 && ping <= 999999) {
      this.pin = pin;
    } else {
      System.out.println("PIN must be a 4-digit number");
    }
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}