package repository.jdbc;

import config.DatabaseConnection;
import entity.User;
import repository.UserRepository;

import java.sql.*;
import java.util.List;

public class JdbcUserRepository implements UserRepository {
    @Override
    public void save(User user) {

        String sql = """
                INSERT INTO users
                (name, username, password, credit,
                 registration_date, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setDouble(4, user.getCredit());

            statement.setTimestamp(
                    5,
                    Timestamp.valueOf(
                            user.getRegistrationDate()
                    )
            );

            statement.setString(
                    6,
                    user.getStatus().name()
            );

            statement.executeUpdate();

            ResultSet resultSet =
                    statement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {

    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }
}
