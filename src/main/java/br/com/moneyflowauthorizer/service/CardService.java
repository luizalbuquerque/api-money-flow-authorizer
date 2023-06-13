package br.com.moneyflowauthorizer.service;

import br.com.moneyflowauthorizer.dto.CardDto;
import br.com.moneyflowauthorizer.entity.CardEntity;
import br.com.moneyflowauthorizer.repository.CardRepository;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface CardService {

        void createCard(CardDto userDto);

        ResponseEntity<CardEntity> findUserById(Long id);

        ResponseEntity<BigDecimal> getAmount(Long id);

        ResponseEntity<String> updateCustumerById(CardDto form, Long id);

        ResponseEntity<String>  deleteById(Long id);

        void isExistentCard(CardRepository cardRepository, CardDto cardDto);


}
