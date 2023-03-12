package com.dummy.bank.system.transaction.producer;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.dummy.bank.system.kafka.config.KafkaConfigData;
import com.dummy.bank.system.kafka.model.Transaction;
import com.dummy.bank.system.transaction.config.TransactionConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@EnableScheduling
public class TransactionProducer {

    private KafkaTemplate<String, Transaction> kafkaTemplate;
    private TransactionConfig transactionConfig;
    private KafkaConfigData kafkaConfigData;

    public TransactionProducer(KafkaTemplate<String, Transaction> kafkaTemplate,
            TransactionConfig transactionConfig,
            KafkaConfigData kafkaConfigData) {

        this.transactionConfig = transactionConfig;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConfigData = kafkaConfigData;
    }

    @Scheduled(fixedRate = 1000)
    public void produce() {
        ObjectMapper mapper = new ObjectMapper();
        String[] fromAccount = transactionConfig.getFromAccounts()[ThreadLocalRandom.current().nextInt(0, 5)];
        String[] toAccount = transactionConfig.getToAccounts()[ThreadLocalRandom.current().nextInt(0, 5)];
        String[] currency = transactionConfig.getCurrency()[ThreadLocalRandom.current().nextInt(0, 3)];

        Transaction transaction = new Transaction();
        transaction.setFrom(fromAccount[0]);
        transaction.setFromAccount(fromAccount[1]);
        transaction.setTo(toAccount[0]);
        transaction.setToAccount(toAccount[1]);
        transaction.setAmount(ThreadLocalRandom.current().nextLong(0, 10000000));
        transaction.setCurrency(currency[0]);
        transaction.setDatetime(Instant.now().toEpochMilli());

        ListenableFuture<SendResult<String, Transaction>> futureResult = kafkaTemplate
                .send(kafkaConfigData.getTransactionTopic(), transaction);

        futureResult.addCallback(new ListenableFutureCallback<SendResult<String, Transaction>>() {
            @Override
            public void onSuccess(SendResult<String, Transaction> result) {
                StringBuilder sb = new StringBuilder();
                try {
                    sb.append("Sent Transaction + " + mapper.writeValueAsString(transaction) + " \n");
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                System.out.println(sb.toString());
            }

            @Override
            public void onFailure(Throwable ex) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to send Transaction from " + transaction.getFromAccount() + " \n");
                sb.append("To " + transaction.getToAccount() + " \n");
                sb.append("due to : " + ex.getMessage());

                System.out.println(sb.toString());
            }
        });

    }

}
