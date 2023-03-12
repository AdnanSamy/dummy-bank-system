package com.dummy.bank.system.proccessor.config;

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
        return new NewTopic(kafkaConfigData.getTransactionTopic(), kafkaConfigData.getNumOfPartitions(),
                kafkaConfigData.getReplicationFactor());
    }

}
