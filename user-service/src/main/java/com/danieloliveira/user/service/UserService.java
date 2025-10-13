package com.danieloliveira.user.service;

import com.danieloliveira.user.*;
import com.danieloliveira.user.service.requestHandler.UserInformationRequestHandler;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase{

    private final UserInformationRequestHandler userInformationRequestHandler;

    public UserService(UserInformationRequestHandler userInformationRequestHandler) {
        this.userInformationRequestHandler = userInformationRequestHandler;
    }

    @Override
    public void getUserInformation(UserInformationRequest request, StreamObserver<UserInformation> responseObserver) {
        var userInformation = this.userInformationRequestHandler.getUserInformation(request);
        responseObserver.onNext(userInformation);
        responseObserver.onCompleted();
    }

    @Override
    public void tradeStock(StockTradeRequest request, StreamObserver<StockTradeResponse> responseObserver) {

    }
}
