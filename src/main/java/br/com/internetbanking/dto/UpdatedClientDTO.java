package br.com.internetbanking.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UpdatedClientDTO {

    private String name;
    private boolean executivePlan;
    private BigDecimal amount;
    private String accountNumber;
    private Date birthday;
}
