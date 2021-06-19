package com.vaibhavbiotech.services;

import com.vaibhavbiotech.models.Product;
import com.vaibhavbiotech.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        Product storedProduct = productRepository.save(product);
        return storedProduct;
    }
}
