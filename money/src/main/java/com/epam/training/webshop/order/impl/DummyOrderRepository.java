package com.epam.training.webshop.order.impl;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.order.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DummyOrderRepository implements OrderRepository {

    public static final Logger LOGGER = LoggerFactory.getLogger(DummyOrderRepository.class);

    @Override
    public void saveOrder(Cart cart) {
        LOGGER.info("Order created with Cart, products= {}", cart.getProducts());
    }
}
