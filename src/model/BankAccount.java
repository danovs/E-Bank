package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private int id;
    private String name;
    private double balance;
    private double annualInterestRate = 0.20;
    private LocalDateTime dateCreated;
    private List<Transaction> transactions;

    public BankAccount() {
        this.dateCreated = LocalDateTime.now();
        this.transactions = new ArrayList<>();
    }

    public BankAccount(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.annualInterestRate = 0.20;
        this.dateCreated = LocalDateTime.now();
        this.transactions = new ArrayList<>();
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getMonthlyInterest() {
        return (annualInterestRate / 12) * balance;
    }

    public double calculateInterest(double amount) {
        return amount * annualInterestRate;
    }

    public void applyMonthlyInterest() {
        double interest = getMonthlyInterest();
        deposit(interest);
    }
//функция снять деньги со счета
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            recordTransaction("Withdrawal", amount, "Снятие со счета");
        } else {
            System.out.println("Недостаточно средств.");
        }
    }
//функция пополнить счет
    public void deposit(double amount) {
        balance += amount;
        recordTransaction("Deposit", amount, "Пополнение счета");
    }
//функция записи транзакии
    private void recordTransaction(String type, double amount, String description) {
        Transaction transaction = new Transaction(type, amount, description);
        transactions.add(transaction);
        System.out.printf("Транзакция выполнена. Новый баланс: %.2f\n", balance);
    }
//функция вывода данных по текущему пользователю
    public String displayAccountSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("\n");
        summary.append(String.format("Владелец:             %s\n", name));
        summary.append(String.format("ID:                   %d\n", id));
        summary.append(String.format("Баланс:               %.2f\n", balance));
        summary.append(String.format("Процентная ставка:    %.2f%%\n", annualInterestRate * 100));
        summary.append(String.format("Дата создания:        %s\n", dateCreated.toLocalDate() + " " + dateCreated.toLocalTime().withSecond(0).withNano(0)));
        summary.append("Транзакции:\n");
        summary.append(String.format("%-19s %-10s %-10s %s\n", "Дата и время", "Тип", "Сумма", "Описание"));

        for (Transaction transaction : transactions) {
            summary.append(String.format("%-19s %-10s %-10.2f %s\n",
                    transaction.getDate().toLocalDate() + " " + transaction.getDate().toLocalTime().withSecond(0).withNano(0),
                    transaction.getType(),
                    transaction.getAmount(),
                    transaction.getDescription()));
        }

        return summary.toString();
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Balance: " + balance + ", Interest Rate: " + annualInterestRate + ", Date Created: " + dateCreated;
    }
}
