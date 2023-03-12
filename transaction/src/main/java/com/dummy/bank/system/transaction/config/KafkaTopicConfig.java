package com.dummy.bank.system.transaction.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import com.dummy.bank.system.kafka.config.KafkaConfigData;

@Configuration
public class KafkaTopicConfig {

    private KafkaConfigData kafkaConfigData;

    public KafkaTopicConfig(KafkaConfigData kafkaConfigData) {
        this.kafkaConfigData = kafkaConfigData;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers());

        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic transactionTopic() {
        System.out.println("TRX TOPIC -> " + kafkaConfigData.getTransactionTopic());
        System.out.println("NUM OF -> " + kafkaConfigData.getNumOfPartitions());
        System.out.println("REPLICATE -> " + kafkaConfigData.getReplicationFactor());

        return new NewTopic(kafkaConfigData.getTransactionTopic(), kafkaConfigData.getNumOfPartitions(),
                kafkaConfigData.getReplicationFactor());
    }

}
