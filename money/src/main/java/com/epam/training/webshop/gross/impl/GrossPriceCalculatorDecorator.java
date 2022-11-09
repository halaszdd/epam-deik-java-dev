package com.epam.training.webshop.gross.impl;

import com.epam.training.webshop.cart.ShoppingCartService;
import com.epam.training.webshop.gross.GrossPriceCalculator;

public class GrossPriceCalculatorDecorator implements GrossPriceCalculator {

    private final GrossPriceCalculator grossPriceCalculator;

    public GrossPriceCalculatorDecorator(final GrossPriceCalculator grossPriceCalculator) {
        this.grossPriceCalculator = grossPriceCalculator;
    }

    @Override
    public double getAggregatedGrossPrice(final ShoppingCartService shoppingCartService) {
        return grossPriceCalculator.getAggregatedGrossPrice(shoppingCartService);
    }
}
