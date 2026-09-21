package config;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void createTables() {

        String usersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    username VARCHAR(100) UNIQUE NOT NULL,
                    password VARCHAR(100) NOT NULL,
                    credit NUMERIC(12, 2) NOT NULL DEFAULT 0,
                    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    status VARCHAR(20) NOT NULL
                )
                """;
        String transactionsTable = """
                CREATE TABLE IF NOT EXISTS transactions (
                    id SERIAL PRIMARY KEY,
                    user_id INT NOT NULL,
                    amount NUMERIC(12, 2) NOT NULL,
                    type VARCHAR(30) NOT NULL,
                    description VARCHAR(255),
                    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                
                    CONSTRAINT fk_transaction_user
                        FOREIGN KEY (user_id)
                        REFERENCES users(id)
                )
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             Statement statement =
                     connection.createStatement()) {

            statement.executeUpdate(usersTable);
            statement.executeUpdate(transactionsTable);

            System.out.println("Tables created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
