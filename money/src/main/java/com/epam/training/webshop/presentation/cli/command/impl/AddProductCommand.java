package com.epam.training.webshop.presentation.cli.command.impl;

import com.epam.training.webshop.cart.ShoppingCartService;
import com.epam.training.webshop.cart.exception.UnknownProductException;
import com.epam.training.webshop.presentation.cli.command.Command;
import com.epam.training.webshop.product.ProductRepository;

public class AddProductCommand implements Command {

    private final ProductRepository productRepository;
    private final ShoppingCartService shoppingCartService;
    private final String productNameToAdd;

    public AddProductCommand(ProductRepository productRepository, ShoppingCartService shoppingCartService, String productNameToAdd) {
        this.productRepository = productRepository;
        this.shoppingCartService = shoppingCartService;
        this.productNameToAdd = productNameToAdd;
    }

    @Override
    public String execute() {
        try {
            shoppingCartService.addProduct(productNameToAdd);
            return "Alright.";
        } catch (UnknownProductException e) {
            return "No such product";
        }
    }
}
