package br.com.moneyflowauthorizer.service;

import br.com.moneyflowauthorizer.dto.TransactionDTO;
import br.com.moneyflowauthorizer.entity.TransactionEntity;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    void newTransaction(TransactionDTO transactionDTO);

    List<TransactionEntity> getTransactionsByDate(LocalDate date);
}
