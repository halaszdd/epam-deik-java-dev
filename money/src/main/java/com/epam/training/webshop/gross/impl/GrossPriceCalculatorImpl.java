package com.epam.training.webshop.gross.impl;

import com.epam.training.webshop.cart.ShoppingCartService;
import com.epam.training.webshop.gross.GrossPriceCalculator;
import org.springframework.stereotype.Component;

@Component
public class GrossPriceCalculatorImpl implements GrossPriceCalculator {
    @Override
    public double getAggregatedGrossPrice(final ShoppingCartService shoppingCartService) {
        return shoppingCartService.getTotalNetPrice();
    }
}
