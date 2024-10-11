package model;
import java.time.LocalDateTime;
public class BankAccount {

    private int id;
    private double balance;
    private double annualInterestRate;
    private LocalDateTime dateCreated;

    public BankAccount() {
        this.dateCreated = LocalDateTime.now();
    }

    public BankAccount(int id, double balance) {
        this.id = id;
        this.balance = balance;
        this.dateCreated = LocalDateTime.now();
    }

    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public double getAnnualInterestRate() {
        return annualInterestRate;
    }
    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
    public double getMonthlyInterest() {
        return (annualInterestRate / 12) * balance;
    }
    public void applyMonthlyInterest() {double interest = getMonthlyInterest(); deposit(interest);}
    public void withdraw(double amount) { if (amount <= balance) { balance -= amount;} else { System.out.println("Недостаточно средств.");}}
    public void deposit(double amount) { balance += amount;}
    public String toString() { return "ID: " + id + ", Баланс: " + balance + ", Процентная ставка: " + annualInterestRate + ", Дата создания: " + dateCreated; }

}
