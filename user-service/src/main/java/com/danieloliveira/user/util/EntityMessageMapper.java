package com.danieloliveira.user.util;

import com.danieloliveira.user.Holding;
import com.danieloliveira.user.StockTradeRequest;
import com.danieloliveira.user.StockTradeResponse;
import com.danieloliveira.user.UserInformation;
import com.danieloliveira.user.entity.PortfolioItem;
import com.danieloliveira.user.entity.User;

import java.util.List;

public class EntityMessageMapper {

    public static UserInformation toUserInformation(User user, List<PortfolioItem> portfolioItems) {
        List<Holding> holdings = portfolioItems.stream()
                .map(i -> Holding.newBuilder().setTicker(i.getTicker()).setQuantity(i.getQuantity()).build())
                .toList();

        return UserInformation.newBuilder()
                .setUserId(user.getId())
                .setName(user.getName())
                .setBalance(user.getBalance())
                .addAllHoldings(holdings)
                .build();

    }

    public static PortfolioItem toPortfolioItem(StockTradeRequest request) {
        var portfolioItem = new PortfolioItem();
        portfolioItem.setUserId(request.getUserId());
        portfolioItem.setTicker(request.getTicker());
        portfolioItem.setQuantity(request.getQuantity());
        return portfolioItem;
    }

    public static StockTradeResponse toStockTradeResponse(StockTradeRequest request, int balance) {
        return StockTradeResponse.newBuilder()
                .setUserId(request.getUserId())
                .setPrice(request.getPrice())
                .setTicker(request.getTicker())
                .setQuantity(request.getQuantity())
                .setAction(request.getAction())
                .setTotalPrice(request.getPrice() * request.getQuantity())
                .setBalance(balance)
                .build();
    }
}
