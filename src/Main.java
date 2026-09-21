import config.DatabaseInitializer;
import entity.Transaction;
import entity.User;
import repository.jdbc.JdbcTransactionRepository;
import repository.jdbc.JdbcUserRepository;
import service.UserService;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        DatabaseInitializer.createTables();

        Scanner scanner = new Scanner(System.in);

        JdbcUserRepository userRepository =
                new JdbcUserRepository();

        JdbcTransactionRepository transactionRepository =
                new JdbcTransactionRepository();

        UserService userService =
                new UserService(
                        userRepository,
                        transactionRepository
                );

        while (true) {

            System.out.println();
            System.out.println("===== SMS PANEL =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    register(scanner, userService);
                    break;

                case 2:
                    login(scanner, userService);
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void register(
            Scanner scanner,
            UserService userService) {

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.register(
                name,
                username,
                password
        );

        if (user == null) {
            System.out.println("Username already exists.");
        } else {
            System.out.println("Registration successful.");
            System.out.println(
                    "Your initial credit: "
                            + user.getCredit()
            );
        }
    }

    public static void login(
            Scanner scanner,
            UserService userService) {

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.login(
                username,
                password
        );

        if (user == null) {
            System.out.println("Login failed.");
        } else {
            System.out.println("Login successful.");
            System.out.println(
                    "Welcome " + user.getName()
            );

            userMenu(
                    scanner,
                    userService,
                    user
            );
        }
    }

    public static void userMenu(
            Scanner scanner,
            UserService userService,
            User user) {

        while (true) {

            System.out.println();
            System.out.println("===== USER MENU =====");
            System.out.println("1. View Profile");
            System.out.println("2. Increase Credit");
            System.out.println("3. Change Password");
            System.out.println("4. Transaction History");
            System.out.println("5. Deactivate Account");
            System.out.println("6. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    viewProfile(userService, user);
                    break;

                case 2:
                    increaseCredit(
                            scanner,
                            userService,
                            user
                    );
                    break;

                case 3:
                    changePassword(
                            scanner,
                            userService,
                            user
                    );
                    break;

                case 4:
                    transactionHistory(
                            userService,
                            user
                    );
                    break;

                case 5:
                    if (deactivateAccount(
                            userService,
                            user
                    )) {
                        return;
                    }
                    break;

                case 6:
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void viewProfile(
            UserService userService,
            User user) {

        User currentUser =
                userService.getProfile(user.getId());

        System.out.println();
        System.out.println("===== PROFILE =====");
        System.out.println(
                "Name: " + currentUser.getName()
        );
        System.out.println(
                "Username: " + currentUser.getUsername()
        );
        System.out.println(
                "Credit: " + currentUser.getCredit()
        );
        System.out.println(
                "Registration Date: "
                        + currentUser.getRegistrationDate()
        );
        System.out.println(
                "Status: " + currentUser.getStatus()
        );
    }

    public static void increaseCredit(
            Scanner scanner,
            UserService userService,
            User user) {

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        boolean result =
                userService.increaseCredit(
                        user.getId(),
                        amount
                );

        if (result) {
            user.setCredit(
                    user.getCredit() + amount
            );

            System.out.println(
                    "Credit increased successfully."
            );

            System.out.println(
                    "Current credit: "
                            + user.getCredit()
            );

        } else {
            System.out.println(
                    "Credit increase failed."
            );
        }
    }

    public static void changePassword(
            Scanner scanner,
            UserService userService,
            User user) {

        System.out.print(
                "Enter current password: "
        );
        String currentPassword =
                scanner.nextLine();

        System.out.print(
                "Enter new password: "
        );
        String newPassword =
                scanner.nextLine();

        boolean result =
                userService.changePassword(
                        user.getId(),
                        currentPassword,
                        newPassword
                );

        if (result) {
            user.setPassword(newPassword);

            System.out.println(
                    "Password changed successfully."
            );
        } else {
            System.out.println(
                    "Current password is incorrect."
            );
        }
    }

    public static void transactionHistory(
            UserService userService,
            User user) {

        List<Transaction> transactions =
                userService.getTransactionHistory(
                        user.getId()
                );

        System.out.println();
        System.out.println("===== TRANSACTION HISTORY =====");

        if (transactions.isEmpty()) {
            System.out.println(
                    "No transactions found."
            );
            return;
        }

        for (Transaction transaction : transactions) {

            System.out.println(
                    "ID: " + transaction.getId()
            );

            System.out.println(
                    "Amount: " + transaction.getAmount()
            );

            System.out.println(
                    "Type: " + transaction.getType()
            );

            System.out.println(
                    "Description: "
                            + transaction.getDescription()
            );

            System.out.println(
                    "Date: " + transaction.getDate()
            );

            System.out.println("-------------------------");
        }
    }

    public static boolean deactivateAccount(
            UserService userService,
            User user) {

        boolean result =
                userService.deactivateAccount(
                        user.getId()
                );

        if (result) {
            System.out.println(
                    "Account deactivated successfully."
            );

            System.out.println(
                    "You have been logged out."
            );

            return true;
        }

        System.out.println(
                "Account deactivation failed."
        );

        return false;
    }
}