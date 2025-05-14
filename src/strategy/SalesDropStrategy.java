package strategy;
import model.SalesData;
import model.Product;

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

        boolean sharpDrop = salesLast30 >= 5 && salesLast7 <= 1;
        boolean lowStock = product.getStock() < 10;

        if (sharpDrop && lowStock) {
            return Optional.of("Model '" + product.getModel() + "' (SKU: " + product.getSku() + ") - Current Stock: " + product.getStock());
        }

        boolean highStock = product.getStock() >= 10;

        if (sharpDrop && highStock) {
            return Optional.of(
                    "Model '" + product.getModel() +
                            "' (SKU: " + product.getSku() + ") – Current Stock: " + product.getStock());
        }



        return Optional.empty();
    }
}
