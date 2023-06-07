package br.com.moneyflowauthorizer.service.impl;

import br.com.moneyflowauthorizer.dto.CardDto;
import br.com.moneyflowauthorizer.entity.CardEntity;
import br.com.moneyflowauthorizer.exeption.BusinessException;
import br.com.moneyflowauthorizer.repository.CardRepository;
import br.com.moneyflowauthorizer.service.CardService;
import br.com.moneyflowauthorizer.service.ClientService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.moneyflowauthorizer.constant.ConstantUtils.DUPLICATE_USER;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    @Override
    public void createCard(CardDto cardDto) {

        isExistentClient(cardRepository, cardDto);

        try {
            CardEntity clientEntity = new CardEntity();
            clientEntity.setName(cardDto.getName());
            clientEntity.setBirthday(cardDto.getBirthday());
            clientEntity.setAmount(cardDto.getAmount());
            clientEntity.setAccountNumber(cardDto.getAccountNumber());
            clientEntity.setExecutivePlan(cardDto.getExecutivePlan());
            cardRepository.save(clientEntity);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(DUPLICATE_USER);
        }
    }

    @Override
    public ResponseEntity<CardEntity> findUserById(Long id) {
        try {
            Optional<CardEntity> clientOptional = cardRepository.findById(id);
            if (!clientOptional.isPresent()) {
                throw new BusinessException("Cliente com número de ID: " + id + " não foi encontrado no sistema!");
            }
            CardEntity client = clientOptional.get();
            return ResponseEntity.ok(client);
        } catch (EmptyResultDataAccessException ex) {
            throw new BusinessException("Cliente com número de ID: " + id + " não foi encontrado no sistema!");
        }
    }


    @Override
    public ResponseEntity<String> updateCustumerById(CardDto form, Long id) {
        try {
            CardEntity existingClient = cardRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Cliente com número de ID: " + id + " não foi encontrado no sistema!"));

            // Atualizar os campos do cliente com base nos dados do form
            existingClient.setName(form.getName());
            existingClient.setBirthday(form.getBirthday());
            existingClient.setAmount(form.getAmount());
            existingClient.setAccountNumber(form.getAccountNumber());
            existingClient.setExecutivePlan(form.getExecutivePlan());

            // Salvar as alterações no banco de dados
            cardRepository.save(existingClient);

            return ResponseEntity.ok("Cliente atualizado com sucesso");
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar cliente: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<String> deleteById(Long id) {
        if (cardRepository.existsById(id)) {
            cardRepository.deleteById(id);
            return ResponseEntity.ok("Cliente removido com sucesso");
        } else {
            throw new BusinessException("Cliente com número de ID: " + id + " não foi encontrado no sistema");
        }
    }


    @Override
    public void isExistentClient(CardRepository cardRepository, CardDto cardDto) throws BusinessException {
        if (cardRepository.existsByName(cardDto.getName())) {
            throw new BusinessException("Cliente " + cardDto.getName() + " já cadastrado no sistema!");
        }
        if (cardRepository.existsByAccountNumber(cardDto.getAccountNumber())) {
            throw new BusinessException("Conta " + cardDto.getAccountNumber() + " já cadastrada no sistema!");
        }
    }
}


