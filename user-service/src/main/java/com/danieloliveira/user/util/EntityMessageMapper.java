package com.danieloliveira.user.util;

import com.danieloliveira.user.Holding;
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
}
