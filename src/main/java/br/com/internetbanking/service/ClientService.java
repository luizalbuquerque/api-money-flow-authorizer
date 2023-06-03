package br.com.internetbanking.service;

import br.com.internetbanking.dto.ClientDto;
import br.com.internetbanking.dto.UpdatedClientDTO;
import br.com.internetbanking.entity.ClientEntity;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ClientService {
        void createUser(ClientDto userDto);

        Optional<ClientEntity> findUserById(Long id);

        ResponseEntity<ClientEntity> updateUser(long id);

    ClientDto updateByUserId(UpdatedClientDTO form, Long id);

        void deleteById(Long id);
    }
