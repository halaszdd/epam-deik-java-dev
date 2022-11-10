package com.epam.training.webshop.presentation.cli.handler;

import com.epam.training.webshop.cart.ShoppingCartService;
import com.epam.training.webshop.cart.exception.UnknownProductException;
import com.epam.training.webshop.order.Order;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.util.CollectionUtils;

@ShellComponent
public class CartCommandHandler {

    private final ShoppingCartService shoppingCartService;

    public CartCommandHandler(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @ShellMethod(value = "Order products", key = "order cart")
    @ShellMethodAvailability(value = "isCartNotEmpty")
    public String orderCart() {
        Order order = shoppingCartService.order();
        return String.format("Ordered products= %s, total net price= %s HUF, total gross price= %s",
                order.getOrderedProducts(), order.getTotalNetPrice(), order.getTotalGrossPrice());

    }

    @ShellMethod(value = "Adds product to the cart", key = "add product")
    public String addProduct(final String productName) {
        try {
            shoppingCartService.addProduct(productName);
            return "Alright";
        } catch (UnknownProductException e) {
            return "No such product";
        }
    }

    private Availability isCartNotEmpty() {
        if (CollectionUtils.isEmpty(shoppingCartService.getProducts())) {
            return Availability.unavailable("Can't order az empty cart");
        } else {
            return Availability.available();
        }
    }
}
