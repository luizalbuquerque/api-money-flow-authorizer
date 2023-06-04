package br.com.internetbanking.service;

import br.com.internetbanking.dto.ClientDto;
import br.com.internetbanking.dto.UpdatedClientDTO;
import br.com.internetbanking.entity.ClientEntity;
import br.com.internetbanking.repository.ClientRepository;
import org.springframework.http.ResponseEntity;

public interface ClientService {

        void createClient(ClientDto userDto);

        ResponseEntity<ClientEntity> findUserById(Long id);

        ResponseEntity<String> updateCustumerById(ClientDto form, Long id);

        ResponseEntity<String>  deleteById(Long id);

        void isExistentClient(ClientRepository clientRepository, ClientDto clientDto);


}
