package br.com.internetbanking.enun;



public enum TransactionType {

    WITHDRAWAL("Saque"),
    DEPOSIT("Depósito"),
    TRANSFER("Transferência");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
