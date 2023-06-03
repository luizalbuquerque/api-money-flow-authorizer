package br.com.internetbanking.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class ClientDto {

    private String name;
    private Boolean executivePlan;
    private BigDecimal amount;
    private String accountNumber;
    private Date birthday;


}
