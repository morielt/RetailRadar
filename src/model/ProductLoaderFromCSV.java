package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductLoaderFromCSV {
    public static List<Product> load(String pathToCsv) throws IOException {
        List<Product> products = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(pathToCsv));
        String line;
        reader.readLine(); // skip header

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] values = line.split(",");
            if (values.length != 6) continue;

            String sku = values[0].trim();
            String model = values[1].trim();
            String brand = values[2].trim();
            double price = Double.parseDouble(values[3].trim());
            int stock = Integer.parseInt(values[4].trim());
            LocalDate dateAdded = LocalDate.parse(values[5].trim());
            Product p = new Product(sku, model, brand, price, stock,dateAdded);
            products.add(p);
        }
        reader.close();
        return products;
    }

}
