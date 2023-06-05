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

    public void deposit(TransactionDTO transactionDTO) {
        Long clientId = transactionDTO.getId();
        BigDecimal amount = transactionDTO.getAmount();

        Optional<ClientEntity> clientOptional = clientRepository.findById(clientId);
        if (!clientOptional.isPresent()) {
            throw new BusinessException("Cliente não encontrado");
        }

        // Updating balance
        ClientEntity client = clientOptional.get();
        BigDecimal newBalance = client.getAmount().add(amount);
        client.setAmount(newBalance);

        // Creating a new transaction
        TransactionEntity depositTransaction = new TransactionEntity();
        depositTransaction.setId(clientId);
        depositTransaction.setType(TransactionType.DEPOSIT);
        depositTransaction.setValue(amount);
        depositTransaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(depositTransaction);

        // Retrieve all transactions for the client
        List<TransactionEntity> transactionList = client.getTransactions();
        if (transactionList == null) {
            transactionList = new ArrayList<>();
        }

        // Add the new deposit transaction to the list
        transactionList.add(depositTransaction);

        // Update the client's transaction list
        client.setTransactions(transactionList);

        clientRepository.save(client);
    }


    @Override
    public ResponseEntity<List<TransactionEntity>> getAllTransactions() {
        return null;
    }

}
