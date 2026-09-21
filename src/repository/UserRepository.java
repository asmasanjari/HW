package repository;

import entity.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    void update(User user);

    User findById(int id);

    List<User> findAll();

    User findByUsername(String username);
}