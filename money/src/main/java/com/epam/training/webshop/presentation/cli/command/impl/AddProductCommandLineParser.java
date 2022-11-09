package com.epam.training.webshop.presentation.cli.command.impl;

import com.epam.training.webshop.cart.ShoppingCartService;
import com.epam.training.webshop.presentation.cli.command.AbstractCommandLineParser;
import com.epam.training.webshop.presentation.cli.command.Command;
import com.epam.training.webshop.product.ProductRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(2)
public class AddProductCommandLineParser extends AbstractCommandLineParser {

    private static final String COMMAND_REGEX = "add product (.+)";
    private final ProductRepository productRepository;
    private final ShoppingCartService shoppingCartService;

    public AddProductCommandLineParser(ProductRepository productRepository, ShoppingCartService shoppingCartService) {
        this.productRepository = productRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    protected boolean canCreateCommand(String commandLine) {
        return commandLine.matches(COMMAND_REGEX);
    }

    @Override
    protected Command doCreateCommand(String commandLine) {
        Matcher matcher = Pattern.compile(COMMAND_REGEX).matcher(commandLine);
        matcher.matches();
        String productName = matcher.group(1);
        return new AddProductCommand(productRepository, shoppingCartService, productName);
    }
}
