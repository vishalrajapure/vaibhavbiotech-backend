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
    public Boolean uploadImage(@RequestParam MultipartFile file,
                               @RequestParam String productName,
                               @RequestParam String price,
                               @RequestParam String description,
                               @RequestParam PlantType plantType) {
        //need to check if product with same name exist?
        System.out.println("Started adding product : " + productName);
        String[] tokenizedNameArr = file.getOriginalFilename().split("\\.");
        String updatedFileName = productName + "." + tokenizedNameArr[tokenizedNameArr.length - 1];
        boolean result = this.productServiceImpl.uploadImageViaFTP(file, updatedFileName);
        if (result) {
            Product product = new Product();
            product.setProductName(productName);
            product.setPrice(price);
            product.setDescription(description);
            product.setPlantType(plantType);
            product.setImageLink("https://www.vaibhavbiotech.com/webimages/" + productName + "." + tokenizedNameArr[tokenizedNameArr.length - 1]);
            Product dbProduct = this.productServiceImpl.addProductToDb(product);
            if (dbProduct != null) {
                System.out.println("Product added Successfully : " + productName);
                return true;
            }
        }
        return false;
    }

    @GetMapping("/GetProducts")
    public List<Product> getAllProducts() {
        System.out.println("Fetching all products");
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

    @GetMapping("/HealthCheck")
    public String healthCheck() {
        return "App is up and running";
    }

}
