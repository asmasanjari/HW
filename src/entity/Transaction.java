package entity;

import java.time.LocalDateTime;

public class Transaction {

    private int id;
    private int userId;
    private double amount;
    private TransactionType type;
    private String description;
    private LocalDateTime date;

    public Transaction() {
    }

    public Transaction(int userId,
                       double amount,
                       TransactionType type,
                       String description) {

        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.date = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}