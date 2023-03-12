package com.dummy.bank.system.transaction.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dummy.bank.system.kafka.model.Transaction;
import com.dummy.bank.system.transaction.consumer.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TransactionConsumer {

    private TransactionService transactionService;

    public TransactionConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "idr-transaction-topic")
    public void consumeTransaction(Transaction transaction) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Received message -> " + mapper.writeValueAsString(transaction));

        transactionService.insert(transaction);
    }
}
