package repository.jdbc;

import config.DatabaseConnection;
import entity.Transaction;
import entity.TransactionType;
import repository.TransactionRepository;

import java.sql.*;
import java.util.ArrayList;
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
        String sql = """
                UPDATE transactions
                SET amount = ?,
                    type = ?,
                    description = ?,
                    date = ?
                WHERE id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDouble(
                    1,
                    transaction.getAmount()
            );

            statement.setString(
                    2,
                    transaction.getType().name()
            );

            statement.setString(
                    3,
                    transaction.getDescription()
            );

            statement.setTimestamp(
                    4,
                    Timestamp.valueOf(
                            transaction.getDate()
                    )
            );

            statement.setInt(
                    5,
                    transaction.getId()
            );

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Transaction findById(int id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapTransaction(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM transactions";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                transactions.add(mapTransaction(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }

    @Override
    public List<Transaction> findByUserId(int userId) {
        List<Transaction> transactions = new ArrayList<>();

        String sql =
                "SELECT * FROM transactions WHERE user_id = ? ORDER BY date";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                transactions.add(mapTransaction(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }

    private Transaction mapTransaction(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();

        transaction.setId(resultSet.getInt("id"));
        transaction.setUserId(resultSet.getInt("user_id"));
        transaction.setAmount(resultSet.getDouble("amount"));

        transaction.setType(
                TransactionType.valueOf(
                        resultSet.getString("type")
                )
        );

        transaction.setDescription(
                resultSet.getString("description")
        );

        transaction.setDate(
                resultSet.getTimestamp("date").toLocalDateTime()
        );

        return transaction;
    }
}
