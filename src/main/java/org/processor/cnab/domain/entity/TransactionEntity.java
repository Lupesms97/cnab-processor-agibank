package org.processor.cnab.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transaction_cnab")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer type;
    private Date date;
    private BigDecimal amount;
    private Long cpf;
    private String card;
    private Time hour;
    @Column(name = "store_owner")
    private String storeOwner;
    @Column(name = "store_name")
    private String storeName;

    public TransactionEntity(Integer type, Date date, BigDecimal amount, Long cpf, String card, Time hour, String storeOwner, String storeName) {
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.cpf = cpf;
        this.card = card;
        this.hour = hour;
        this.storeOwner = storeOwner;
        this.storeName = storeName;
    }
}
