package com.vaibhavbiotech.services;

import com.vaibhavbiotech.models.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);

    List<Product> getAllProducts();

    Product updateProduct(Product product);

    String deleteProduct(long id);
}
