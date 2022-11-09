package com.epam.training.webshop.gross;

import com.epam.training.webshop.cart.ShoppingCartService;

public interface GrossPriceCalculator {

    double getAggregatedGrossPrice(ShoppingCartService shoppingCartService);
}
