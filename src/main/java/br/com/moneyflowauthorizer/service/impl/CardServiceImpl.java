package br.com.moneyflowauthorizer.service.impl;

import br.com.moneyflowauthorizer.dto.CardDto;
import br.com.moneyflowauthorizer.entity.CardEntity;
import br.com.moneyflowauthorizer.enun.CardStatus;
import br.com.moneyflowauthorizer.exeption.BusinessException;
import br.com.moneyflowauthorizer.repository.CardRepository;
import br.com.moneyflowauthorizer.service.CardService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

        isExistentCard(cardRepository, cardDto);

        try {
            CardEntity cardEntity = new CardEntity();
            cardEntity.setName(cardDto.getName());
            cardEntity.setAmount(cardDto.getAmount());
            cardEntity.setNumberCard(cardDto.getNumberCard());
            cardEntity.setCardStatus(CardStatus.ATIVO);
            cardEntity.setPassword(cardDto.getPassword());
            cardRepository.save(cardEntity);
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
    public ResponseEntity<BigDecimal> getAmount(Long id) {
        try {
            Optional<CardEntity> cardOptional = cardRepository.findById(id);
            if (!cardOptional.isPresent()) {
                throw new BusinessException("Cliente com ID: " + id + " não foi encontrado no sistema!");
            }
            CardEntity card = cardOptional.get();

            CardDto cardDto = new CardDto();
            cardDto.setAmount(card.getAmount());

            return ResponseEntity.ok(cardDto.getAmount());
        } catch (EmptyResultDataAccessException ex) {
            throw new BusinessException("Cliente com ID:: " + id + " não foi encontrado no sistema!");
        }
    }

    @Override
    public ResponseEntity<String> updateCustumerById(CardDto form, Long id) {
        try {
            CardEntity existingClient = cardRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Cliente com número de ID: " + id + " não foi encontrado no sistema!"));

            // Atualizar os campos do cliente com base nos dados do form
            existingClient.setName(form.getName());
            existingClient.setAmount(form.getAmount());
            existingClient.setNumberCard(form.getNumberCard());
            existingClient.setCardStatus(CardStatus.ATIVO);
            existingClient.setPassword(form.getPassword());

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
    public void isExistentCard(CardRepository cardRepository, CardDto cardDto) throws BusinessException {
        if (cardRepository.findCardByNumberCard(cardDto.getNumberCard()).isPresent()) {
            throw new BusinessException("Cartão de numero: " + cardDto.getNumberCard() + " já cadastrado no sistema!");
        }
        if (cardRepository.findByName(cardDto.getName()).isPresent()) {
            throw new BusinessException("Titular para cartão com nome: " + cardDto.getName() + " já cadastrado no sistema!");
        }
    }
}


