package com.dummy.bank.system.transaction.config;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class TransactionConfig {
    private String[][] fromAccounts = {
        {"Andi", "09809809809809"},
        {"Sam", "123123213121322"},
        {"Haryono", "345345344544"},
        {"Tono", "097987489393993"},
        {"Suneo", "1232421343333"},
    };
    private String[][] toAccounts = {
        {"Siti", "3423423423423"},
        {"Tina", "342342333324"},
        {"Haryati", "123123232"},
        {"Fina", "4343243223343"},
        {"Vini", "345df32d23434"},
    };
    private String[][] currency = {
        {"IDR", "0"},
        {"USD", "15000"},
        {"EUR", "16522"},
    };
}
