package br.com.internetbanking.service.impl;

import br.com.internetbanking.dto.TransactionDTO;
import br.com.internetbanking.entity.ClientEntity;
import br.com.internetbanking.entity.TransactionEntity;
import br.com.internetbanking.enun.TransactionType;
import br.com.internetbanking.exeption.BusinessException;
import br.com.internetbanking.repository.ClientRepository;
import br.com.internetbanking.repository.TransactionRepository;
import br.com.internetbanking.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(ClientRepository clientRepository, TransactionRepository transactionRepository) {
        this.clientRepository = clientRepository;
        this.transactionRepository = transactionRepository;
    }

// VERSÂO DEPOSIT
//    public void newTransaction(TransactionDTO request) {
//
//        ClientEntity client = clientRepository.findByAccountNumber(request.getAccountNumber())
//                .orElseThrow(() -> new IllegalArgumentException("Client not found."));
//
//        // Updating balance
//        BigDecimal newBalance = client.getAmount().add(request.getValue());
//        client.setAmount(newBalance);
//
//        // Creating a new transaction
//        TransactionEntity transaction = new TransactionEntity();
//        transaction.setType(TransactionType.DEPOSIT);
//        transaction.setValue(request.getValue());
//        transaction.setTransactionDate(LocalDateTime.now());
//        transaction.setClient(client);
//
//        transactionRepository.save(transaction);
//
//        // Retrieve all transactions for the client
//        List<TransactionEntity> transactionList = client.getTransactions();
//        if (transactionList == null) {
//            transactionList = new ArrayList<>();
//        }
//
//        // Add the new deposit transaction to the list
//        transactionList.add(transaction);
//
//
//        //Update the client's transaction list
//        client.setTransactions(transactionList);
//        clientRepository.save(client);
//
//    }


    public void newTransaction(TransactionDTO request) {
        ClientEntity client = getClientByAccountNumber(request.getAccountNumber());

        if (request.getType() == TransactionType.DEPOSIT) {
            // Transação de DEPÓSITO
            updateClientBalance(client, request.getValue());
        } else if (request.getType() == TransactionType.WITHDRAWAL) {
            // Transação de SAQUE
            BigDecimal withdrawalAmount = calculateWithdrawalAmount(request.getValue(), client.isExecutivePlan());
            updateClientBalance(client, withdrawalAmount.negate());
        } else {
            throw new IllegalArgumentException("Tipo de transação inválido.");
        }

        TransactionEntity transaction = createTransaction(request, client);
        saveTransaction(transaction);
        updateClientTransactionList(client, transaction);
    }

    private ClientEntity getClientByAccountNumber(String accountNumber) {
        return clientRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Client not found."));
    }

    private void updateClientBalance(ClientEntity client, BigDecimal value) {
        BigDecimal newBalance = client.getAmount().add(value);
        client.setAmount(newBalance);
    }

    private TransactionEntity createTransaction(TransactionDTO request, ClientEntity client) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setValue(request.getValue());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setClient(client);
        return transaction;
    }

    private void saveTransaction(TransactionEntity transaction) {
        transactionRepository.save(transaction);
    }

    private void updateClientTransactionList(ClientEntity client, TransactionEntity transaction) {
        List<TransactionEntity> transactionList = client.getTransactions();
        if (transactionList == null) {
            transactionList = new ArrayList<>();
        }
        transactionList.add(transaction);
        client.setTransactions(transactionList);
        clientRepository.save(client);
    }

    private BigDecimal calculateWithdrawalAmount(BigDecimal value, boolean isExecutivePlan) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }

        BigDecimal withdrawalAmount;
        if (value.compareTo(BigDecimal.valueOf(100)) <= 0) {
            // Valor <= 100,00: Isento de taxa
            withdrawalAmount = value;
        } else if (value.compareTo(BigDecimal.valueOf(300)) <= 0) {
            // Valor > 100,00 e <= 300,00: Taxa de 0.4%
            BigDecimal feePercentage = isExecutivePlan ? BigDecimal.ZERO : BigDecimal.valueOf(0.004);
            BigDecimal feeAmount = value.multiply(feePercentage);
            withdrawalAmount = value.subtract(feeAmount);
        } else {
            // Valor > 300,00: Taxa de 1%
            BigDecimal feePercentage = isExecutivePlan ? BigDecimal.ZERO : BigDecimal.valueOf(0.01);
            BigDecimal feeAmount = value.multiply(feePercentage);
            withdrawalAmount = value.subtract(feeAmount);
        }

        return withdrawalAmount;
    }


    @Override
    public ResponseEntity<List<TransactionEntity>> getAllTransactions() {
        return null;
    }

}
