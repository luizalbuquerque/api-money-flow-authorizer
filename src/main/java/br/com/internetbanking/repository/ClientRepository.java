package br.com.internetbanking.repository;

import br.com.internetbanking.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    boolean existsByName(String name);
    Optional<ClientEntity> findByAccountNumber(String accountNumber);
    boolean existsByAccountNumber(String accountNumber);


}
