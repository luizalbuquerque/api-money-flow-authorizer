package br.com.internetbanking.service.impl;

import br.com.internetbanking.dto.ClientDto;
import br.com.internetbanking.dto.UpdatedClientDTO;
import br.com.internetbanking.entity.ClientEntity;
import br.com.internetbanking.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Override
    public void createUser(ClientDto userDto) {

    }

    @Override
    public Optional<ClientEntity> findUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public ResponseEntity<ClientEntity> updateUser(long id) {
        return null;
    }

    @Override
    public ClientDto updateByUserId(UpdatedClientDTO form, Long id) {
        return null;
    }


    @Override
    public void deleteById(Long id) {

    }
}
