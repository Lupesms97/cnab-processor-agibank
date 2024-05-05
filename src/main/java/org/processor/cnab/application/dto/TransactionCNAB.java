package org.processor.cnab.application.dto;

import java.math.BigDecimal;

public record TransactionCNAB(
        Integer type,
        String date,
        BigDecimal amount,
        Long cpf,
        String card,
        String time,
        String storeOwner,
        String storeName

) {
}