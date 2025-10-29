package com.danieloliveira.aggregator.service;

import com.danieloliveira.stock.StockPriceRequest;
import com.danieloliveira.stock.StockServiceGrpc;
import com.danieloliveira.user.StockTradeRequest;
import com.danieloliveira.user.StockTradeResponse;
import com.danieloliveira.user.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class TradeService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userClient;

    @GrpcClient("stock-service")
    private StockServiceGrpc.StockServiceBlockingStub stockClient;

    public StockTradeResponse trade(StockTradeRequest request) {
        var priceRequest = StockPriceRequest.newBuilder().setTicker(request.getTicker()).build();
        var priceResponse = this.stockClient.getStockPrice(priceRequest);
        var tradeRequest = StockTradeRequest.newBuilder().setTicker(priceResponse.getTicker()).build();
        return this.userClient.tradeStock(tradeRequest);
    }
}
