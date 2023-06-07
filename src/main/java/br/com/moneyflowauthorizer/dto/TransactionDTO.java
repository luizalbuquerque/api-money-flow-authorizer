package br.com.internetbanking.dto;


import br.com.internetbanking.enun.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Long id;
    private String accountNumber;
    private TransactionType type;
    private BigDecimal value;
    private LocalDateTime transactionDate;

    public TransactionType getTransactionType() {
        return this.type;
    }

}
