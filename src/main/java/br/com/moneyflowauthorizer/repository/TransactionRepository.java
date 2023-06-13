package br.com.moneyflowauthorizer.repository;

import br.com.moneyflowauthorizer.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByTransactionDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

}
