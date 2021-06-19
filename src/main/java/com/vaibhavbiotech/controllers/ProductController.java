package com.vaibhavbiotech.controllers;

import com.vaibhavbiotech.models.Product;
import com.vaibhavbiotech.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @PostMapping("/AddProduct")
    public Product addProduct(@RequestBody Product product) {
        Product storedProduct = productServiceImpl.addProduct(product);
        return storedProduct;
    }

    @GetMapping("/GetProducts")
    public List<Product> getAllProducts() {
        List<Product> productList = productServiceImpl.getAllProducts();
        return null;
    }

}
