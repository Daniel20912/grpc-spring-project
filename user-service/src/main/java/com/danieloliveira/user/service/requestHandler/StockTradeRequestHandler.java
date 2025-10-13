package com.danieloliveira.user.service.requestHandler;

import com.danieloliveira.common.Ticker;
import com.danieloliveira.user.StockTradeRequest;
import com.danieloliveira.user.StockTradeResponse;
import com.danieloliveira.user.exception.InsufficientBalanceException;
import com.danieloliveira.user.exception.UnknowTickerException;
import com.danieloliveira.user.exception.UnknowUserException;
import com.danieloliveira.user.repository.PortfolioItemRepository;
import com.danieloliveira.user.repository.UserRepository;
import com.danieloliveira.user.util.EntityMessageMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StockTradeRequestHandler {

    private final UserRepository userRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public StockTradeRequestHandler(UserRepository userRepository, PortfolioItemRepository portfolioItemRepository) {
        this.userRepository = userRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    @Transactional // em caso de alguma exceção será feito um rollback
    public StockTradeResponse buyStock(StockTradeRequest request) {
        // validate
        this.validateTicker(request.getTicker());

        var user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UnknowUserException(request.getUserId()));

        var totalPrice = request.getQuantity() * request.getPrice();
        this.validateUserBalance(user.getId(), user.getBalance(), totalPrice);

        // valid request
        user.setBalance(user.getBalance() - totalPrice);
        this.portfolioItemRepository.findByUserIdAndTicker(user.getId(), request.getTicker())
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + request.getQuantity()),
                        () -> this.portfolioItemRepository.save(EntityMessageMapper.toPortfolioItem(request))
                );

        return EntityMessageMapper.toStockTradeResponse(request, user.getBalance());
    }

    private void validateTicker(Ticker ticker) {
        if (Ticker.UNKNOWN.equals(ticker)) {
            throw new UnknowTickerException();
        }
    }

    private void validateUserBalance(Integer userId, Integer userBalance, Integer totalPrice) {
        if (totalPrice < userBalance) {
            throw new InsufficientBalanceException(userId);
        }
    }
}
