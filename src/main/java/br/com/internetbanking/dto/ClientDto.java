package br.com.internetbanking.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ClientDto {

    private String name;
    private boolean executivePlan;
    private BigDecimal amount;
    private String accountNumber;
    private Date birthday;

    public ClientDto(String name, boolean executivePlan, BigDecimal amount, String accountNumber, Date birthday) {
        this.name = name;
        this.executivePlan = executivePlan;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExecutivePlan() {
        return executivePlan;
    }

    public void setExecutivePlan(boolean executivePlan) {
        this.executivePlan = executivePlan;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
