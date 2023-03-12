package com.dummy.bank.system.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String from;
    private String fromAccount;
    private String to;
    private String toAccount;
    private Long amount;
    private String currency;
    private Long datetime;
}
