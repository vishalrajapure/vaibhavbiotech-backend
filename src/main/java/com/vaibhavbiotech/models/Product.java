package com.vaibhavbiotech.models;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCTS")
public class Product extends AuditModel {
    @Id
    @SequenceGenerator(
            name = "client_sequence",
            sequenceName = "client_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "client_sequence"
    )
    private long id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private String price;
    @Column(name = "image_link")
    private String imageLink;
    @Column(name = "plant_type")
    private PlantType plantType;
    @Column(name = "show_on_home_page")
    private boolean showOnHomePage;

    public Product() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }

    public boolean isShowOnHomePage() {
        return showOnHomePage;
    }

    public void setShowOnHomePage(boolean showOnHomePage) {
        this.showOnHomePage = showOnHomePage;
    }
}
