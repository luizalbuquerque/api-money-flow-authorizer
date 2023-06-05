package br.com.internetbanking.service.impl;

import br.com.internetbanking.dto.ClientDto;
import br.com.internetbanking.entity.ClientEntity;
import br.com.internetbanking.exeption.BusinessException;
import br.com.internetbanking.repository.ClientRepository;
import br.com.internetbanking.service.ClientService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public void createClient(ClientDto clientDto) {

        isExistentClient(clientRepository, clientDto);

        // Validação, troca feita pela chamada de método reutilizável acima.
//        if (clientRepository.existsByName(clientDto.getName())) {
//            throw new BusinessException("Cliente " + clientDto.getName() + " já cadastrado no sistema.");
//        }

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
    public ResponseEntity<ClientEntity> findUserById(Long id) {
        try {
            Optional<ClientEntity> clientOptional = clientRepository.findById(id);
            if (!clientOptional.isPresent()) {
                throw new BusinessException("Cliente com número de ID: " + id + " não foi encontrado no sistema!");
            }
            ClientEntity client = clientOptional.get();
            return ResponseEntity.ok(client);
        } catch (EmptyResultDataAccessException ex) {
            throw new BusinessException("Cliente com número de ID: " + id + " não foi encontrado no sistema!");
        }
    }


    @Override
    public ResponseEntity<String> updateCustumerById(ClientDto form, Long id) {
        try {
            ClientEntity existingClient = clientRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Cliente com número de ID: " + id + " não foi encontrado no sistema!"));

            // Atualizar os campos do cliente com base nos dados do form
            existingClient.setName(form.getName());
            existingClient.setBirthday(form.getBirthday());
            existingClient.setAmount(form.getAmount());
            existingClient.setAccountNumber(form.getAccountNumber());
            existingClient.setExecutivePlan(form.getExecutivePlan());

            // Salvar as alterações no banco de dados
            clientRepository.save(existingClient);

            return ResponseEntity.ok("Cliente atualizado com sucesso");
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar cliente: " + e.getMessage());
        }
    }



    @Override
    public ResponseEntity<String> deleteById(Long id) {
            if (clientRepository.existsById(id)) {
                clientRepository.deleteById(id);
                return ResponseEntity.ok("Cliente removido com sucesso");
            } else {
                throw new BusinessException("Cliente com número de ID: " + id + " não foi encontrado no sistema");
            }
    }


    @Override
    public void isExistentClient(ClientRepository clientRepository, ClientDto clientDto) throws BusinessException {
            if (clientRepository.existsByName(clientDto.getName())) {
                throw new BusinessException("Cliente " + clientDto.getName() + " já cadastrado no sistema!");
            }
        }
    }


