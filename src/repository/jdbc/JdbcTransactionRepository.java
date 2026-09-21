package repository.jdbc;

import entity.Transaction;
import repository.TransactionRepository;

import java.util.List;

public class JdbcTransactionRepository
        implements TransactionRepository {
    @Override
    public void save(Transaction transaction) {

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
