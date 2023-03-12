package com.dummy.bank.system.transaction.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.dummy.bank.system.kafka.config.KafkaConfigData;
import com.dummy.bank.system.kafka.model.Transaction;

@EnableKafka
@Configuration
public class TransactionConsumerConfig {

        private KafkaConfigData kafkaConfigData;

        public TransactionConsumerConfig(KafkaConfigData kafkaConfigData) {
                this.kafkaConfigData = kafkaConfigData;
        }

        @Bean
        public ConsumerFactory<String, Transaction> consumerFactory() {
                JsonDeserializer<Transaction> jsonDeserializer = new JsonDeserializer<>(Transaction.class);
                jsonDeserializer.addTrustedPackages("*");
                jsonDeserializer.setUseTypeHeaders(false);
                // jsonDeserializer.

                Map<String, Object> props = new HashMap<>();
                props.put(
                                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                                kafkaConfigData.getBootstrapServers());
                props.put(
                                ConsumerConfig.GROUP_ID_CONFIG,
                                "A");
                props.put(
                                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                                StringDeserializer.class);
                props.put(
                                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                                JsonDeserializer.class);
                return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                                jsonDeserializer);
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, Transaction> kafkaListenerContainerFactory() {

                ConcurrentKafkaListenerContainerFactory<String, Transaction> factory = new ConcurrentKafkaListenerContainerFactory<>();
                factory.setConsumerFactory(consumerFactory());
                return factory;
        }

}
