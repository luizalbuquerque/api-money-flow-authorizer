package br.com.moneyflowauthorizer.dto;

import br.com.moneyflowauthorizer.enun.CardStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class CardDto {

    private String name;
    private BigDecimal amount;
    private String numberCard;
    private CardStatus status;
    private  String password;

    @CreationTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    private Date createdAt;

    @UpdateTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    private Date updatedAt;



}
