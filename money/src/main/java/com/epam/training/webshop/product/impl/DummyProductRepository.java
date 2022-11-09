package com.epam.training.webshop.product.impl;

import com.epam.training.webshop.product.Product;
import com.epam.training.webshop.product.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DummyProductRepository implements ProductRepository {

    @Value("${products.apple.name}")
    private String appleName;


    @Value("${products.apple.price:99}")
    private Double applePrice;


    @Value("${products.apple.packaging}")
    private String applePackaging;


    @Value("${products.melon.name}")
    private String melonName;


    @Value("${products.melon.price:199}")
    private Double melonPrice;

    @Value("#{'${products.caviar}'.split(',')}")
    private List<String> caviarDetails;

    private List<Product> products;

    private void initProducts() {
        this.products = List.of(
                SimpleProduct.builder(appleName).withNetPrice(applePrice).withPackaging(applePackaging).build(),
                SimpleProduct.builder(melonName).withNetPrice(melonPrice).build(),
                SimpleProduct.builder(caviarDetails.get(0)).withNetPrice(Double.parseDouble(caviarDetails.get(1))).withPackaging(caviarDetails.get(2)).build()
        );
    }

    @Override
    public List<Product> getProducts() {
        return this.products;
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return this.products.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
    }
}
