package com.vaibhavbiotech.services;

import com.vaibhavbiotech.models.Product;
import com.vaibhavbiotech.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        Product storedProduct = productRepository.save(product);
        return storedProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Product productRequest) {
        Product productFromDb = productRepository.getById(productRequest.getId());
        productFromDb.setProductName(productRequest.getProductName());
        productFromDb.setDescription(productRequest.getDescription());
        productFromDb.setPrice(productRequest.getPrice());
        productFromDb.setImageLink(productRequest.getImageLink());
        productFromDb.setPlantType(productRequest.getPlantType());
        productFromDb.setShowOnHomePage(productRequest.isShowOnHomePage());
        return productRepository.save(productFromDb);
    }

    @Override
    public String deleteProduct(long id) {
        productRepository.deleteById(id);
        return "product deleted";
    }

    @Override
    public List<Product> getLatestProducts() {
        List<Product> latestProducts = new ArrayList<>();
        productRepository.findAll().forEach(product -> {
            if (product.isShowOnHomePage())
                latestProducts.add(product);
        });
        return latestProducts;
    }
}
