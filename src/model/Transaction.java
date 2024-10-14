package model;

import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime date;
    private String type;
    private double amount;
    private String description;

    public Transaction(String type, double amount, String description) {
        this.date = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.description = description;
    }


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