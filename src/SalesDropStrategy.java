import java.time.LocalDate;
import java.util.Optional;

public class SalesDropStrategy implements InsightStrategy {

    public Optional<String> analyze(Product product, SalesData salesData) {
        return Optional.empty();
    }

    @Override
    public Optional<String> analyze(Product product, SalesData salesData, LocalDate analysisDate) {
        int salesLast30 = salesData.getSoldSince(product.getSku(), analysisDate.minusDays(30));
        int salesLast7 = salesData.getSoldSince(product.getSku(), analysisDate.minusDays(7));

        boolean sharpDrop = salesLast30 >= 10 && salesLast7 <= 1;
        boolean lowStock = product.getStock() < 10;

        if (sharpDrop && lowStock) {
            return Optional.of("⚠️ Sales drop alert: Model '" + product.getModel() +
                    "' (SKU: " + product.getSku() + ") had strong sales in the past 30 days (" + salesLast30 +
                    "), but only " + salesLast7 + " units sold in the last 7 days. Current stock is low (" +
                    product.getStock() + "). Consider restocking.");
        }

        return Optional.empty();
    }
}
