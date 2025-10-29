package com.danieloliveira.aggregator.controller;

import com.danieloliveira.aggregator.service.TradeService;
import com.danieloliveira.user.StockTradeRequest;
import com.danieloliveira.user.StockTradeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping
    public StockTradeResponse trade(@RequestBody StockTradeRequest request){
        return this.tradeService.trade(request);
    }
}
