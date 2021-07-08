package com.vaibhavbiotech.services;

import com.vaibhavbiotech.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Product addProductToDb(Product product);

    List<Product> getAllProducts();

    Product updateProduct(Product product);

    Long uploadImageViaFTP(MultipartFile file, String extension);

    boolean deleteProduct(long id);

    List<Product> getLatestProducts();
}
