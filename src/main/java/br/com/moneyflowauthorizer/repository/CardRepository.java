package br.com.moneyflowauthorizer.repository;

import br.com.moneyflowauthorizer.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<CardEntity, Long> {

    boolean existsByName(String name);
    Optional<CardEntity> findByAccountNumber(String accountNumber);
    boolean existsByAccountNumber(String accountNumber);


}
