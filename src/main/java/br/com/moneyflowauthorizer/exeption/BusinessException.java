package br.com.moneyflowauthorizer.exeption;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}