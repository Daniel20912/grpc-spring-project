package com.danieloliveira.user.service.advice;

import com.danieloliveira.user.exception.InsufficientBalanceException;
import com.danieloliveira.user.exception.InsufficientSharesException;
import com.danieloliveira.user.exception.UnknowTickerException;
import com.danieloliveira.user.exception.UnknowUserException;
import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class ServiceExceptionHandler {

    @GrpcExceptionHandler(UnknowTickerException.class)
    public Status handleInvalidArguments(UnknowTickerException exception) {
        return Status.INVALID_ARGUMENT.withDescription(exception.getMessage());
    }

    @GrpcExceptionHandler(UnknowUserException.class)
    public Status handleUnknowEntity(UnknowUserException exception) {
        return Status.NOT_FOUND.withDescription(exception.getMessage());
    }

    @GrpcExceptionHandler({InsufficientBalanceException.class, InsufficientSharesException.class})
    public Status handlePreconditionalFailures(Exception exception) {
        return Status.FAILED_PRECONDITION.withDescription(exception.getMessage());
    }
}
