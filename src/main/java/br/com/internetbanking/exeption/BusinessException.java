package br.com.internetbanking.exeption;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}