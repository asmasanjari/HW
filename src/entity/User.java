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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(
            LocalDateTime registrationDate) {

        this.registrationDate = registrationDate;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}

