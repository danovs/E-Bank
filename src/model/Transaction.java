package model;

import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime date; // Date of transaction
    private String type;        // Type of transaction (Deposit or Withdrawal)
    private double amount;      // Amount of transaction
    private String description; // Description of the transaction

    // Constructor
    public Transaction(String type, double amount, String description) {
        this.date = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    // Getters
    public LocalDateTime getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}