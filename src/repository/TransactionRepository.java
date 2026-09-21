package repository;

import entity.Transaction;

import java.util.List;

public interface TransactionRepository {

    void save(Transaction transaction);

    void update(Transaction transaction);

    Transaction findById(int id);

    List<Transaction> findAll();

    List<Transaction> findByUserId(int userId);
}
