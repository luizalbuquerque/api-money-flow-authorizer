package br.com.moneyflowauthorizer.dto;


import br.com.moneyflowauthorizer.enun.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Long id;
    private String numberCard;
    private TransactionType type;
    private BigDecimal value;
    private String password;
    private LocalDateTime transactionDate;

    public TransactionType getTransactionType() {
        return this.type;
    }

}
