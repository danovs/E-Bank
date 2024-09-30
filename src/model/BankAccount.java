package model;
import org.w3c.dom.CDATASection;

import java.util.Date;
public class BankAccount {

    private int id;
    private double balance;
    private double annualInterestRate;
    private Date dateCreated;

    public BankAccount() {
        this.id = 0;
        this.balance = 0.0;
        this.annualInterestRate = 0.0;
        this.dateCreated = new Date();
    }

    public BankAccount(int id, double balance) {
        this.id = id;
        this.balance = balance;
        this.annualInterestRate = 0.0;
        this.dateCreated = new Date();
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
    public void setBalance() {
        this.balance = balance;
    }
    public double getAnnualInterestRate() {
        return annualInterestRate;
    }
    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }
    public Date getDateCreated() {
        return dateCreated;
    }
    public double getMonthlyInterest() {
        return (annualInterestRate / 12) * balance;
    }
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
        }
        else {
            System.out.println("Недостаточно средств.");
        }
    }
    public void deposit(double amount) {
        balance += amount;
    }

}
