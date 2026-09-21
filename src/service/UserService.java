package service;

import entity.User;
import repository.TransactionRepository;
import repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public UserService(UserRepository userRepository,
                       TransactionRepository transactionRepository) {

        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public User register(String name, String username, String password) {
        User existingUser =
                userRepository.findByUsername(username);
        if (existingUser != null) {
            return null;
        }
    }
}
