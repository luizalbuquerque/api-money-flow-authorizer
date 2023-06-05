package br.com.internetbanking.repository;

import br.com.internetbanking.entity.ClientEntity;
import br.com.internetbanking.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByTransactionDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

}
