package Main;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.Product;
import model.ProductLoaderFromCSV;
import model.SalesData;
import strategy.InsightStrategy;
import strategy.SalesDropStrategy;
import strategy.ShelfTimeStrategy;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MainController {

    @FXML private Button loadButton;
    @FXML private Button shelfButton;
    @FXML private Button dropButton;
    @FXML private Button bestButton;
    @FXML private ComboBox<String> brandFilter;
    @FXML private TextArea outputArea;

    private List<Product> products;
    private SalesData salesData;
    private final LocalDate now = LocalDate.now();
    private boolean isBrandFilterVisible = false;

    @FXML
    public void initialize() {
        loadButton.setOnAction(e -> loadCSVFiles());
        shelfButton.setOnAction(e -> runShelfStrategy());
        dropButton.setOnAction(e -> runDropStrategy());

        brandFilter.setVisible(false);
        bestButton.setOnAction(e -> {
            if (!isBrandFilterVisible) {
                brandFilter.setVisible(true);
                isBrandFilterVisible = true;
            } else {
                showBestSellers();
            }
        });

        brandFilter.setOnAction(e -> {
            if (brandFilter.getValue() != null) {
                showBestSellers();
            }
        });
    }

    private void loadCSVFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select both Products and Sales CSV files (any order)");
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(loadButton.getScene().getWindow());

        if (selectedFiles == null || selectedFiles.size() != 2) {
            outputArea.setText("❌ You must select exactly TWO CSV files – Sales & Products.");
            return;
        }

        File file1 = selectedFiles.get(0);
        File file2 = selectedFiles.get(1);

        File productsFile;
        File salesFile;

        try {
            if (isProductsFile(file1)) {
                productsFile = file1;
                salesFile = file2;
            } else if (isProductsFile(file2)) {
                productsFile = file2;
                salesFile = file1;
            } else {
                outputArea.setText("❌ Could not identify the products file. Make sure it contains headers like 'model' or 'brand'.");
                return;
            }

            products = ProductLoaderFromCSV.load(productsFile.getAbsolutePath());
            salesData = new SalesData(salesFile.getAbsolutePath());

            Set<String> brands = products.stream()
                    .map(Product::getBrand)
                    .collect(Collectors.toCollection(TreeSet::new));

            brandFilter.getItems().clear();
            brandFilter.getItems().add("All Brands");
            brandFilter.getItems().addAll(brands);
            brandFilter.setValue("All Brands");

            brandFilter.setVisible(false);
            isBrandFilterVisible = false;

            outputArea.setText("✅ Files loaded successfully.\n📦 Products: " + products.size() + "\n📈 Sales: " + salesData.getAllSales().size());

        } catch (IOException ex) {
            outputArea.setText("❌ Error loading files: " + ex.getMessage());
        }
    }

    private boolean isProductsFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            if (!scanner.hasNextLine()) return false;
            String header = scanner.nextLine().toLowerCase();
            String[] columns = header.split(",");

            return columns.length >= 6 || Arrays.stream(columns).anyMatch(col ->
                    col.contains("model") || col.contains("brand") || col.contains("price"));
        } catch (IOException e) {
            return false;
        }
    }

    private void runShelfStrategy() {
        if (products == null || salesData == null) {
            outputArea.setText("⚠️ Please load CSV files first.");
            return;
        }

        InsightStrategy strategy = new ShelfTimeStrategy(30);
        StringBuilder sb = new StringBuilder("📦 Shelf Time Alert\n");
        sb.append("Products with prolonged shelf time and no recent sales – consider removing or refreshing\n\n");

        boolean found = false;
        for (Product product : products) {
            Optional<String> result = strategy.analyze(product, salesData, now);
            if (result.isPresent()) {
                sb.append(result.get()).append("\n");
                found = true;
            }
        }

        if (!found) {
            sb.append("✔️ No products matched this insight.");
        }

        outputArea.setText(sb.toString());
    }

    private void runDropStrategy() {
        if (products == null || salesData == null) {
            outputArea.setText("⚠️ Please load CSV files first.");
            return;
        }

        InsightStrategy strategy = new SalesDropStrategy();
        StringBuilder sb = new StringBuilder("📉 Sales Drop Insight\n");
        sb.append("Products with declining sales – check inventory availability and restocking delays\n\n");

        sb.append("If inventory is sufficient, consider price adjustments or promotional strategies\n\n");

        boolean found = false;
        for (Product product : products) {
            Optional<String> result = strategy.analyze(product, salesData, now);
            if (result.isPresent()) {
                sb.append(result.get()).append("\n");
                found = true;
            }
        }

        if (!found) {
            sb.append("✔️ No products matched this insight.");
        }

        outputArea.setText(sb.toString());
    }

    private void showBestSellers() {
        if (products == null || salesData == null) {
            outputArea.setText("⚠️ Please load CSV files first.");
            return;
        }

        String selectedBrand = brandFilter.getValue();
        StringBuilder sb = new StringBuilder("🏆 Best Sellers – " + selectedBrand + "\n\n");

        products.stream()
                .filter(p -> {
                    int sold = salesData.getSoldSince(p.getSku(), now.minusDays(30));
                    return sold > 0 && ("All Brands".equals(selectedBrand) || p.getBrand().equalsIgnoreCase(selectedBrand));
                })
                .sorted(Comparator.comparingInt(p -> -salesData.getSoldSince(p.getSku(), now.minusDays(30))))
                .limit(5)
                .forEach(p -> {
                    int sold = salesData.getSoldSince(p.getSku(), now.minusDays(30));
                    sb.append(p.getModel()).append(" (").append(p.getSku()).append(") - Sold: ")
                            .append(sold).append(" units\n");
                });

        outputArea.setText(sb.toString());
    }
}
