package com.dummy.bank.system.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.dummy.bank.system.kafka.config.KafkaConfigData;


@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = "com.dummy.bank.system")
public class App implements CommandLineRunner{

    @Autowired
    KafkaConfigData kafkaConfigData;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("CONFIG DATA -> " + kafkaConfigData.getBootstrapServers());
    }
}