package com.danieloliveira.user.service.requestHandler;

import com.danieloliveira.user.UserInformation;
import com.danieloliveira.user.UserInformationRequest;
import com.danieloliveira.user.entity.PortfolioItem;
import com.danieloliveira.user.exception.UnknowUserException;
import com.danieloliveira.user.repository.PortfolioItemRepository;
import com.danieloliveira.user.repository.UserRepository;
import com.danieloliveira.user.util.EntityMessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// essa classe serve para separar algumas responsabilidades da regras de negócios, para não fazer tudo no UserService
public class UserInformationRequestHandler {

    private final UserRepository userRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public UserInformationRequestHandler(UserRepository userRepository, PortfolioItemRepository portfolioItemRepository) {
        this.userRepository = userRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    public UserInformation getUserInformation(UserInformationRequest request) {
        var user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UnknowUserException(request.getUserId()));

        List<PortfolioItem> portfolioItems = this.portfolioItemRepository.findAllByUserId(user.getId());

        return EntityMessageMapper.toUserInformation(user, portfolioItems);
    }
}
