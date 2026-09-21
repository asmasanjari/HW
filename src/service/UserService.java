package service;

import entity.AccountStatus;
import entity.Transaction;
import entity.TransactionType;
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


    public User register(String name,
                         String username,
                         String password) {

        User existingUser =
                userRepository.findByUsername(username);

        if (existingUser != null) {
            System.out.println("Username already exists.");
            return null;
        }
        User user = new User(name, username, password);

        userRepository.save(user);
        Transaction giftTransaction =
                new Transaction(
                        user.getId(),
                        user.getCredit(),
                        TransactionType.CREDIT,
                        "Registration gift"
                );

        transactionRepository.save(giftTransaction);

        return user;
    }

    public User login(String username, String password) {

        User user =
                userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        if (!user.getPassword().equals(password)) {
            return null;
        }

        if (user.getStatus() == AccountStatus.INACTIVE) {
            return null;
        }

        return user;
    }
}