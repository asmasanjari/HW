package entity;

import java.time.LocalDateTime;

public class User {

    private int id;
    private String name;
    private String username;
    private String password;
    private double credit;
    private LocalDateTime registrationDate;
    private AccountStatus status;

    public User() {
    }

    public User(String name,
                String username,
                String password) {

        this.name = name;
        this.username = username;
        this.password = password;
        this.credit = 10000;
        this.registrationDate = LocalDateTime.now();
        this.status = AccountStatus.ACTIVE;
    }

