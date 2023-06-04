package br.com.internetbanking.repository;

import br.com.internetbanking.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    boolean existsByName(String name);

}
