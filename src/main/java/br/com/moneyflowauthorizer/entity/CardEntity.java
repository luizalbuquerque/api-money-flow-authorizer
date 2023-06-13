package br.com.moneyflowauthorizer.entity;

import br.com.moneyflowauthorizer.enun.CardStatus;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "card")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number_card", nullable = false)
    @NotBlank( message = "Card number is mandatory!")
    private String numberCard;

    @Column(name = "card_status")
    private CardStatus cardStatus;

    @Column(name = "password")
    private String password;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionEntity> transactions = new ArrayList<>();


}
