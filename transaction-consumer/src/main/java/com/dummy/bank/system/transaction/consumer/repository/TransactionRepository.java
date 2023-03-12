package com.dummy.bank.system.transaction.consumer.repository;

import java.util.List;

import com.dummy.bank.system.kafka.model.Transaction;

public interface TransactionRepository {
    public void insert(Transaction transaction);
    public List<Transaction> getAll();
}
