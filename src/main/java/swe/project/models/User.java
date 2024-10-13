package main.java.swe.project.models;

public class User {
  private String name;
  private double balance;

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

  public void setBalance(double balance) {
    this.balance = balance;
  }
}