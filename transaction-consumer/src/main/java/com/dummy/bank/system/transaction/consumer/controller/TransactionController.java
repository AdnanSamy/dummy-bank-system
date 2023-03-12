package com.dummy.bank.system.transaction.consumer.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dummy.bank.system.kafka.model.Transaction;
import com.dummy.bank.system.transaction.consumer.service.TransactionService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/transaction")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Transaction> transactions = transactionService.getAll();

        return ResponseEntity.ok(transactions);
    }
}
