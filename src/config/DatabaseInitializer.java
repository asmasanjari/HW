package config;

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
    }
