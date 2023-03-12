package com.dummy.bank.system.transaction.consumer.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/to/{toName}")
    public ResponseEntity<?> getFilterTo(@PathVariable String toName) {
        List<Transaction> transactions = transactionService.getAll();
        List<Transaction> filteredTransactions = transactions
                .stream()
                .filter((t) -> t.getTo().equalsIgnoreCase(toName))
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredTransactions);
    }

    @GetMapping("/from/{from}")
    public ResponseEntity<?> getFilterFrom(@PathVariable String from) {
        List<Transaction> transactions = transactionService.getAll();
        List<Transaction> filteredTransactions = transactions
                .stream()
                .filter((t) -> t.getFrom().equalsIgnoreCase(from))
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredTransactions);
    }

    @GetMapping("/currency")
    public ResponseEntity<?> getByCurrency() {
        List<Transaction> transactions = transactionService.getByCurrency();

        return ResponseEntity.ok(transactions);
    }
}
