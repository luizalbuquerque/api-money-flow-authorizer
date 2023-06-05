package br.com.internetbanking.repository;

import br.com.internetbanking.entity.ClientEntity;
import br.com.internetbanking.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {


}
