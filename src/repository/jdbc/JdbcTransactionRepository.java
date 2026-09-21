package repository.jdbc;

import config.DatabaseConnection;
import entity.Transaction;
import repository.TransactionRepository;

import java.sql.*;
import java.util.List;

public class JdbcTransactionRepository
        implements TransactionRepository {
    @Override
    public void save(Transaction transaction) {
        String sql = """
                        INSERT INTO transactions
                (user_id, amount, type, description, date)
                VALUES (?, ?, ?, ?, ?)
                """;


        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1,
                    transaction.getUserId());

            statement.setDouble(2,
                    transaction.getAmount());

            statement.setString(3,
                    transaction.getType().name());

            statement.setString(4,
                    transaction.getDescription());

            statement.setTimestamp(5,
                    Timestamp.valueOf(
                            transaction.getDate()
                    ));

            statement.executeUpdate();

            ResultSet resultSet =
                    statement.getGeneratedKeys();

            if (resultSet.next()) {
                transaction.setId(
                        resultSet.getInt(1)
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Transaction transaction) {

    }

    @Override
    public Transaction findById(int id) {
        return null;
    }

    @Override
    public List<Transaction> findAll() {
        return List.of();
    }

    @Override
    public List<Transaction> findByUserId(int userId) {
        return List.of();
    }
}
