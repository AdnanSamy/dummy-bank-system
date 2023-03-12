package com.dummy.bank.system.transaction.consumer.service;

import java.util.List;

import com.dummy.bank.system.kafka.model.Transaction;

public interface TransactionService {
    public void insert(Transaction transaction);
    public List<Transaction> getAll();
}
