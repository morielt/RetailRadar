import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        try {
            List<Product> products = ProductLoaderFromCSV.load("src/products.csv");
            SalesData salesData = new SalesData("src/sales.csv");

            InsightStrategy shelfStrategy = new ShelfTimeStrategy(30);
            InsightStrategy salesDropStrategy = new SalesDropStrategy();

            System.out.println("=== 📊 Insights from ShelfTimeStrategy ===");
            for (Product product : products) {
                shelfStrategy.analyze(product, salesData, LocalDate.now())
                        .ifPresent(System.out::println);
            }

            System.out.println("\n=== 📉 Insights from SalesDropStrategy ===");
            for (Product product : products) {
                salesDropStrategy.analyze(product, salesData, LocalDate.now())
                        .ifPresent(System.out::println);
            }

            System.out.println("\n=== 🏆 Best Sellers ===");
            products.stream()
                    .sorted(Comparator.comparingInt(
                            p -> -salesData.getSoldSince(p.getSku(), LocalDate.now().minusDays(30)))
                    )
                    .limit(5)
                    .forEach(p -> {
                        int sold = salesData.getSoldSince(p.getSku(), LocalDate.now().minusDays(30));
                        System.out.println(p.getModel() + " (" + p.getSku() + ") - Sold: " + sold + " units");
                    });

        } catch (IOException e) {
            System.err.println("⚠️ Error loading data: " + e.getMessage());
        }
    }
}
