package com.vaibhavbiotech.controllers;

import com.vaibhavbiotech.models.PlantType;
import com.vaibhavbiotech.models.Product;
import com.vaibhavbiotech.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/Products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @PostMapping(value = "AddProduct")
    public String uploadImage(@RequestParam MultipartFile file,
                              @RequestParam String productName,
                              @RequestParam String price,
                              @RequestParam String description,
                              @RequestParam PlantType plantType) {

        String result = this.productServiceImpl.uploadImageViaFTP(file);
        String[] arr = file.getOriginalFilename().split("\\.");
        if (result != null && result.equals("success")) {
            Product product = new Product();
            product.setProductName(productName);
            product.setPrice(price);
            product.setDescription(description);
            product.setPlantType(plantType);
            product.setImageLink("www.vaibhavbiotech.com/webimages/" + productName + arr[arr.length - 1]);
            this.productServiceImpl.addProductToDb(product);
            return "Product successfully uploaded";
        }
        return "Upload failed";
    }

    @GetMapping("/GetProducts")
    public List<Product> getAllProducts() {
        return productServiceImpl.getAllProducts();
    }

    @PutMapping("/UpdateProduct")
    public Product updateProduct(@RequestBody Product product) {
        return productServiceImpl.updateProduct(product);
    }

    @DeleteMapping("/DeleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        return productServiceImpl.deleteProduct(id);
    }

    @GetMapping("/GetLatestProducts")
    public List<Product> getLatestProducts() {
        return productServiceImpl.getLatestProducts();
    }

}
