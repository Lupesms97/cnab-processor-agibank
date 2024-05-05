package org.processor.cnab.application.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Data
public class TransactionEntity {
    Long id;
    private Integer type;
    Date date;
    BigDecimal amount;
    String card;
    Long cpf;
    Time time;
    String storeOwner;
    String storeName;
}
