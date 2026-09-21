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
}