package com.dummy.bank.system.proccessor;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dummy.bank.system.kafka.config.KafkaConfigData;
import com.dummy.bank.system.proccessor.serdes.TransactionSerdes;

@Component
public class ConvertToIdr {

    private TransactionSerdes transactionSerdes;
    private KafkaConfigData kafkaConfigData;
    private static final String IDR = "IDR";

    public ConvertToIdr(TransactionSerdes transactionSerdes, KafkaConfigData kafkaConfigData) {
        this.transactionSerdes = transactionSerdes;
        this.kafkaConfigData = kafkaConfigData;
    }

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        streamsBuilder
                .stream(kafkaConfigData.getInputTransactionTopic(),
                        Consumed.with(Serdes.String(), transactionSerdes.transactionSerdes()))
                .mapValues(value -> {
                    if (!value.getCurrency().equals(IDR)) {
                        switch (value.getCurrency()) {
                            case "USD":
                                value.setAmount(value.getAmount() * 15000);
                                break;
                            case "EUR":
                                value.setAmount(value.getAmount() * 16522);
                            default:
                                break;
                        }
                    }
                    return value;
                }).to(kafkaConfigData.getTransactionTopic(),
                        Produced.with(Serdes.String(), transactionSerdes.transactionSerdes()));
    }

}
