package com.danieloliveira.user.exception;

public class InsufficientSharesException extends RuntimeException {

    private static final String MESSAGE = "User [id=%d] does not have enough shares to perform this operation";
    public InsufficientSharesException(int userId) {
        super(String.format(MESSAGE, userId));
    }
}
