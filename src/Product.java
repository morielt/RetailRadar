import java.time.LocalDate;

public class Product {

    private String sku;
    private String model;
    private String brand;
    private double price;
    private int stock;


    public Product(String sku, String model, String brand, double price, int stock) {
        this.sku=sku;
        this.model=model;
        this.brand=brand;
        this.price=price;
        this.stock=stock;
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

};
