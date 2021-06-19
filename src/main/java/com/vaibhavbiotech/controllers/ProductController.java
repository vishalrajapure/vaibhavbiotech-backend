package com.vaibhavbiotech.controllers;

import com.vaibhavbiotech.models.Product;
import com.vaibhavbiotech.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @PostMapping("/Products/AddProduct")
    public Product addProduct(@RequestBody Product product) {
        Product storedProduct = productServiceImpl.addProduct(product);
        return storedProduct;
    }

    @GetMapping
    public List<Product> getProduct() {

        return null;
    }

}
