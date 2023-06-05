package br.com.internetbanking.entity;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "client")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "executive_plan", nullable = false)
    private boolean executivePlan;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionEntity> transactions = new ArrayList<>();


}
