package org.processor.cnab.presentation.mapper;

import org.processor.cnab.application.dto.TransactionCNAB;
import org.processor.cnab.domain.entity.TransactionEntity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FromDto {

    public TransactionEntity toEntity(TransactionCNAB transactionCNAB) {
        return new TransactionEntity(
            transactionCNAB.type(),
                parseDate(transactionCNAB.date(), "yyyyMMdd"),
            transactionCNAB.amount().divide(BigDecimal.valueOf(100)),
            transactionCNAB.cpf(),
            transactionCNAB.card(), parseTime(transactionCNAB.hour(), "HHmmss"),
            transactionCNAB.storeOwner(),
            transactionCNAB.storeName()
        );
    }

    private Date parseDate(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            java.util.Date utilDate = sdf.parse(date);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Erro ao converter data: " + date, e);
        }
    }

    private Time parseTime(String time, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            java.util.Date utilTime = sdf.parse(time);
            return new Time(utilTime.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Erro ao converter tempo: " + time, e);
        }
    }
}
