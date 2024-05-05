package org.processor.cnab.domain.repository;

import org.processor.cnab.domain.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Long>{
}
