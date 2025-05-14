package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class SalesData {
    private Map<String, List<SaleRecord>> salesMap = new HashMap<>();

    public SalesData(String csvPath) throws IOException {
        loadFromCSV(csvPath);
    }

    private void loadFromCSV(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                String sku = parts[0];
                LocalDate date = LocalDate.parse(parts[1]);
                int quantity = Integer.parseInt(parts[2]);

                SaleRecord record = new SaleRecord(sku, date, quantity);
                salesMap.computeIfAbsent(sku, k -> new ArrayList<>()).add(record);
            }
        }
    }

    public int getSoldSince(String sku, LocalDate threshold) {
        List<SaleRecord> sales = salesMap.getOrDefault(sku, List.of());
        return sales.stream()
                .filter(s -> !s.getDateSold().isBefore(threshold))
                .mapToInt(SaleRecord::getQuantity)
                .sum();
    }
    public List<SaleRecord> getAllSales() {
        List<SaleRecord> allSales = new ArrayList<>();
        for (List<SaleRecord> list : salesMap.values()) {
            allSales.addAll(list);
        }
        return allSales;
    }

}
