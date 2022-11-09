package com.epam.training.webshop.cart;

import com.epam.training.webshop.coupon.Coupon;
import com.epam.training.webshop.order.Observable;
import com.epam.training.webshop.product.Product;

import java.util.List;

public interface ShoppingCartService extends Observable {

    List<Product> getProducts();

    void addProduct(Product product);

    void addProduct(String name);

    void removeProduct(Product productToRemove);

    void addCoupon(Coupon coupon);

    List<Coupon> getCouponsFromBasket();

    double getTotalNetPrice();

    double getTotalGrossPrice();

    void order();

    double getBasePrice();

    double getDiscountForCoupons();
}

