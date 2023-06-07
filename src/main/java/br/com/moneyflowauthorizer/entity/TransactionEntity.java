package br.com.internetbanking.entity;

import br.com.internetbanking.enun.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_transaction", nullable = false)
    private TransactionType type;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

}
