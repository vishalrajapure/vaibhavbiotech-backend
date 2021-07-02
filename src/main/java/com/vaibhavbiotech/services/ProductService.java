package com.vaibhavbiotech.services;

import com.vaibhavbiotech.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Product addProductToDb(Product product);

    List<Product> getAllProducts();

    Product updateProduct(Product product);

    boolean uploadImageViaFTP(MultipartFile file, String updatedFileName);

    String deleteProduct(long id);

    List<Product> getLatestProducts();
}
