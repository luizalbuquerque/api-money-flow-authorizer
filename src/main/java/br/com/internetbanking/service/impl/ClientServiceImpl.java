package br.com.internetbanking.service.impl;

import br.com.internetbanking.dto.ClientDto;
import br.com.internetbanking.dto.UpdatedClientDTO;
import br.com.internetbanking.entity.ClientEntity;
import br.com.internetbanking.exeption.BusinessException;
import br.com.internetbanking.repository.ClientRepository;
import br.com.internetbanking.service.ClientService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.internetbanking.constant.ConstantUtils.DUPLICATE_USER;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void createUser(ClientDto clientDto) {

        try {
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setName(clientDto.getName());
            clientEntity.setBirthday(clientDto.getBirthday());
            clientEntity.setAmount(clientDto.getAmount());
            clientEntity.setAccountNumber(clientDto.getAccountNumber());
            clientEntity.setExecutivePlan(clientDto.getExecutivePlan());
            clientRepository.save(clientEntity);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(DUPLICATE_USER);
        }

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
