package com.danieloliveira.user.exception;

public class UnknowTickerException extends RuntimeException {

    private static final String MESSAGE = "Ticker not found";

    public UnknowTickerException() {
        super(MESSAGE);
    }
}
