package repository.jdbc;

import entity.User;
import repository.UserRepository;

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
