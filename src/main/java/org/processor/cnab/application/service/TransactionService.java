package org.processor.cnab.application.service;


import lombok.RequiredArgsConstructor;
import org.processor.cnab.domain.repository.TransactionRepository;
import org.processor.cnab.domain.entity.TransactionEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<TransactionEntity> findAll() {

        Iterable<TransactionEntity> transactions = transactionRepository.findAll();
        transactions.forEach(System.out::println);
        List<TransactionEntity> transactionList = new ArrayList<>();
        transactions.forEach(transactionList::add);

        return transactionList;
    }


}
