package br.com.internetbanking.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class ClientDto {

    private String name;
    private Boolean executivePlan;
    private BigDecimal amount;
    private String accountNumber;

    @DateTimeFormat
    private Date birthday;


}
