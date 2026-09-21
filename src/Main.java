import config.DatabaseInitializer;
import entity.User;
import repository.jdbc.JdbcTransactionRepository;
import repository.jdbc.JdbcUserRepository;
import service.UserService;

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
            System.out.println("Your initial credit: "
                    + user.getCredit());
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
            System.out.println("Welcome " + user.getName());

            userMenu(scanner, userService, user);
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
                    System.out.println("View Profile");
                    break;

                case 2:
                    System.out.println("Increase Credit");
                    break;

                case 3:
                    System.out.println("Change Password");
                    break;

                case 4:
                    System.out.println("Transaction History");
                    break;

                case 5:
                    System.out.println("Deactivate Account");
                    break;

                case 6:
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}