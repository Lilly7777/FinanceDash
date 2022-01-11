package com.financedash.core.exception;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException() {
        super();
    }

    public TransactionNotFoundException(String message) {
        super("Transaction with ID: " + message + " doesn't exist");
    }

    public TransactionNotFoundException(String message, Throwable cause) {
        super("Transaction with ID: " + message + " doesn't exist", cause);
    }
}
