package br.com.moneyflowauthorizer.service.impl;

import br.com.moneyflowauthorizer.dto.TransactionDTO;
import br.com.moneyflowauthorizer.entity.CardEntity;
import br.com.moneyflowauthorizer.entity.TransactionEntity;
import br.com.moneyflowauthorizer.enun.TransactionType;
import br.com.moneyflowauthorizer.exeption.BusinessException;
import br.com.moneyflowauthorizer.repository.CardRepository;
import br.com.moneyflowauthorizer.repository.TransactionRepository;
import br.com.moneyflowauthorizer.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(CardRepository cardRepository, TransactionRepository transactionRepository) {
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
    }

    public void newTransaction(TransactionDTO request) {
        CardEntity card = getCardByNumberCard(request.getNumberCard());

        if (request.getType() == TransactionType.DEPOSIT) {
            // Transação de DEPÓSITO
            updateClientBalance(card, request.getValue());
        } else if (request.getType() == TransactionType.WITHDRAWAL) {
            // Transação de SAQUE
            BigDecimal withdrawalAmount = calculateWithdrawalAmount(request.getValue());
            BigDecimal currentBalance = card.getAmount();
            if (withdrawalAmount.compareTo(currentBalance) > 0) {
                throw new BusinessException("Saldo insuficiente para realizar o saque.");
            }
            updateClientBalance(card, withdrawalAmount.negate());
        } else {
            throw new IllegalArgumentException("Tipo de transação inválido.");
        }

        TransactionEntity transaction = createTransaction(request, card);
        saveTransaction(transaction);
        updateClientTransactionList(card, transaction);
    }

    private CardEntity getCardByNumberCard(String numberCard) {
        return cardRepository.findCardByNumberCard(numberCard)
                .orElseThrow(() -> new IllegalArgumentException("Cartão de numero: " + numberCard +" não encontrado."));
    }


    private void updateClientBalance(CardEntity client, BigDecimal value) {
        BigDecimal newBalance = client.getAmount().add(value);
        client.setAmount(newBalance);
    }


    private TransactionEntity createTransaction(TransactionDTO request, CardEntity card) {
        TransactionEntity transaction = new TransactionEntity();
        if(request.getTransactionType().equals(TransactionType.DEPOSIT)){
            transaction.setType(TransactionType.DEPOSIT);
            transaction.setValue(request.getValue());
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setCard(card);
            return transaction;
        }
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setValue(request.getValue());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setCard(card);
        return transaction;
    }

    private void saveTransaction(TransactionEntity transaction) {
        transactionRepository.save(transaction);
    }

    private void updateClientTransactionList(CardEntity client, TransactionEntity transaction) {
        List<TransactionEntity> transactionList = client.getTransactions();
        if (transactionList == null) {
            transactionList = new ArrayList<>();
        }
        transactionList.add(transaction);
        client.setTransactions(transactionList);
        cardRepository.save(client);
    }

    public List<TransactionEntity> getTransactionsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return transactionRepository.findByTransactionDateBetween(startOfDay, endOfDay);
    }

    private BigDecimal calculateWithdrawalAmount(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }
        return value;
    }

}
