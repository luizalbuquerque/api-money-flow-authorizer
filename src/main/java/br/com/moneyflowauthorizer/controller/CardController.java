package br.com.moneyflowauthorizer.controller;

import br.com.moneyflowauthorizer.dto.CardDto;
import br.com.moneyflowauthorizer.entity.CardEntity;
import br.com.moneyflowauthorizer.exeption.BusinessException;
import br.com.moneyflowauthorizer.repository.CardRepository;
import br.com.moneyflowauthorizer.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    private final CardRepository cardRepository;

    public CardController(CardService cardService, CardRepository cardRepository) {
        this.cardService = cardService;
        this.cardRepository = cardRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> save(@RequestBody CardDto cardDto) {
        try {
            cardService.createCard(cardDto);
            return ResponseEntity.ok("Cartão criado com sucesso");
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Erro ao criar cliente: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CardEntity>> list() {
        List<CardEntity> clients = cardRepository.findAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return cardService.findUserById(id);
            //return ResponseEntity.ok(clientService.findUserById(id));  -> Apresenta headers, boddy, status code..
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Erro ao buscar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/get-amount/{id}")
    public ResponseEntity<?> getAmount(@PathVariable Long id) {
        try {
            return cardService.getAmount(id);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Erro ao buscar cliente: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> update(@RequestBody CardDto form, @PathVariable("id") Long id) {
        return cardService.updateCustumerById(form, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        try {
            return cardService.deleteById(id);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Erro ao deletar cliente: " + e.getMessage());
        }
    }

}
