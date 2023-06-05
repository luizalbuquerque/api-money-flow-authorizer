package br.com.internetbanking.controller;

import br.com.internetbanking.dto.TransactionDTO;
import br.com.internetbanking.entity.TransactionEntity;
import br.com.internetbanking.exeption.BusinessException;
import br.com.internetbanking.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody TransactionDTO transactionDTO) {
        try {
            transactionService.deposit(transactionDTO);
            return ResponseEntity.ok("Depósito realizado com sucesso no valor de: " + transactionDTO.getAmount());
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Erro ao realizar depósito: " + e.getMessage());
        }
    }



}
