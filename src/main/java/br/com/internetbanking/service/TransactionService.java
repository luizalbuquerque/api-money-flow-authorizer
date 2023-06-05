package br.com.internetbanking.service;

import br.com.internetbanking.dto.TransactionDTO;
import br.com.internetbanking.entity.TransactionEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {

    void newTransaction(TransactionDTO transactionDTO);

    ResponseEntity<List<TransactionEntity>> getAllTransactions();


}
