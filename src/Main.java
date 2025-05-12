import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        try {
            // טעינת מוצרים ומכירות
            List<Product> products = ProductLoaderFromCSV.load("src/products.csv");
            SalesData salesData = new SalesData("src/sales.csv");
            Inventory inventory = new Inventory(products);

            int daysBack = 14;
            int topN = 3;

            // Best Sellers (כללי)
            System.out.println("=== 🏆 Top " + topN + " Best Sellers (All Brands) ===");
            List<Product> topSellers = inventory.getTopSellingProducts(topN, daysBack, salesData, Optional.empty());
            for (Product p : topSellers) {
                int sold = salesData.getSoldSince(p.getSku(), LocalDate.now().minusDays(daysBack));
                System.out.printf("- [%s] %s (%s): Sold %d units%n", p.getSku(), p.getModel(), p.getBrand(), sold);
            }

            // מוצרים שלא נמכרו
            System.out.println("\n=== ❌ Unsold Products (Last " + daysBack + " Days) ===");
            List<Product> unsold = inventory.getUnsoldProductsWithinPeriod(daysBack, salesData);
            for (Product p : unsold) {
                System.out.printf("- [%s] %s (%s)%n", p.getSku(), p.getModel(), p.getBrand());
            }

            // Best Sellers לפי מותג
            System.out.println("\n=== 🏆 Top " + topN + " Nike Best Sellers ===");
            List<Product> nikeSellers = inventory.getTopSellingProducts(topN, daysBack, salesData, Optional.of("Nike"));
            for (Product p : nikeSellers) {
                int sold = salesData.getSoldSince(p.getSku(), LocalDate.now().minusDays(daysBack));
                System.out.printf("- [%s] %s: Sold %d units%n", p.getSku(), p.getModel(), sold);
            }

            // בדיקת תקינות כוללת (Sanity Check)
            runSanityCheck(products, salesData, daysBack);

        } catch (IOException e) {
            System.out.println("שגיאה בטעינת הקבצים: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("קלט שגוי: " + e.getMessage());
        }
    }

    public static void runSanityCheck(List<Product> products, SalesData salesData, int daysBack) {
        System.out.println("\n=== ✅ Sanity Check: Products + Sales ===");

        LocalDate threshold = LocalDate.now().minusDays(daysBack);

        for (Product p : products) {
            int sold = salesData.getSoldSince(p.getSku(), threshold);
            System.out.printf("[%s] %-25s | %-10s | Sold: %-3d | Stock: %-3d%n",
                    p.getSku(), p.getModel(), p.getBrand(), sold, p.getStock());
        }

        System.out.println("Total products loaded: " + products.size());
    }
}
