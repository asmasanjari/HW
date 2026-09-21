package repository.jdbc;

import config.DatabaseConnection;
import entity.AccountStatus;
import entity.User;
import repository.UserRepository;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
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
        String sql = """
                UPDATE users
                SET name = ?,
                    username = ?,
                    password = ?,
                    credit = ?,
                    status = ?
                WHERE id = ?
                """;
        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setDouble(4, user.getCredit());
            statement.setString(5,
                    user.getStatus().name());
            statement.setInt(6, user.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findById(int id) {
        String sql =
                "SELECT * FROM users WHERE id = ?";
        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                return mapUser(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }



    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }
    private User mapUser(ResultSet resultSet) throws SQLException {

        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setUsername(
                resultSet.getString("username")
        );
        user.setPassword(
                resultSet.getString("password")
        );
        user.setCredit(
                resultSet.getDouble("credit")
        );

        user.setRegistrationDate(
                resultSet
                        .getTimestamp("registration_date")
                        .toLocalDateTime()
        );

        user.setStatus(
                AccountStatus.valueOf(
                        resultSet.getString("status")
                )
        );

        return user;

    }}

