package br.com.moneyflowauthorizer.service;

import br.com.moneyflowauthorizer.dto.CardDto;
import br.com.moneyflowauthorizer.entity.CardEntity;
import br.com.moneyflowauthorizer.repository.CardRepository;
import org.springframework.http.ResponseEntity;

public interface CardService {

        void createClient(CardDto userDto);

        ResponseEntity<CardEntity> findUserById(Long id);

        ResponseEntity<String> updateCustumerById(CardDto form, Long id);

        ResponseEntity<String>  deleteById(Long id);

        void isExistentClient(CardRepository cardRepository, CardDto cardDto);


}
