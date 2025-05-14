package model;

import java.time.LocalDate;

public class Product {

    private String sku;
    private String model;
    private String brand;
    private double price;
    private int stock;
    private LocalDate dateAdded;


    public Product(String sku, String model, String brand, double price, int stock,LocalDate dateAdded) {
        this.sku=sku;
        this.model=model;
        this.brand=brand;
        this.price=price;
        this.stock=stock;
        this.dateAdded=dateAdded;
    }

    public String getSku() {
        return sku;
    }
    public int getStock() {
        return stock;
    }
    public String getModel() {
        return model;
    }
    public String getBrand() {
        return brand;
    }
    public double getPrice() {
        return price;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }
};
