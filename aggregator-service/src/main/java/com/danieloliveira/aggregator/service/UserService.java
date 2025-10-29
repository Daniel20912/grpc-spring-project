package com.danieloliveira.aggregator.service;

import com.danieloliveira.user.UserInformation;
import com.danieloliveira.user.UserInformationRequest;
import com.danieloliveira.user.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userClient;

    public UserInformation getUserInformation(int userId) {
        var request = UserInformationRequest.newBuilder()
                .setUserId(userId)
                .build();

        return this.userClient.getUserInformation(request);
    }
}
