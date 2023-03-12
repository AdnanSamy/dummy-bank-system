package com.dummy.bank.system.proccessor.serdes;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.context.annotation.Configuration;

import com.dummy.bank.system.kafka.model.Transaction;

@Configuration
public class TransactionSerdes {

    public Serde<Transaction> transactionSerdes() {
        JsonSerializer<Transaction> serializer = new JsonSerializer<>();
        JsonDeserializer<Transaction> deserializer = new JsonDeserializer<>(Transaction.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }
}
