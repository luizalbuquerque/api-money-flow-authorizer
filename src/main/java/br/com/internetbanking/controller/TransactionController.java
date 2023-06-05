package br.com.internetbanking.controller;

import br.com.internetbanking.dto.TransactionDTO;
import br.com.internetbanking.entity.TransactionEntity;
import br.com.internetbanking.enun.TransactionType;
import br.com.internetbanking.exeption.BusinessException;
import br.com.internetbanking.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody TransactionDTO transactionDTO) {
        try {
            TransactionType transactionType = transactionDTO.getTransactionType();
            if (transactionType == TransactionType.WITHDRAWAL) {
                transactionService.newTransaction(transactionDTO);
                return ResponseEntity.ok("Saque realizado com sucesso no valor de: " + transactionDTO.getValue());
            } else if (transactionType == TransactionType.DEPOSIT) {
                transactionService.newTransaction(transactionDTO);
                return ResponseEntity.ok("Depósito realizado com sucesso no valor de: " + transactionDTO.getValue());
            } else {
                return ResponseEntity.badRequest().body("Tipo de transação inválido.");
            }
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Erro ao realizar transação: " + e.getMessage());
        }
    }

    @GetMapping("/byDate")
    public ResponseEntity<List<TransactionEntity>> getTransactionsByDate(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<TransactionEntity> transactions = transactionService.getTransactionsByDate(date);
        return ResponseEntity.ok(transactions);
    }



}
