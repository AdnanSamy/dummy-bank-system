package com.dummy.bank.system.transaction.consumer.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dummy.bank.system.kafka.model.Transaction;
import com.dummy.bank.system.transaction.consumer.repository.TransactionRepository;
import com.dummy.bank.system.transaction.consumer.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void insert(Transaction transaction) {
        transactionRepository.insert(transaction);
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.getAll();
    }

}
