package strategy;
import model.SalesData;
import model.Product;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class ShelfTimeStrategy implements InsightStrategy {

    private final int thresholdDays;

    public ShelfTimeStrategy(int thresholdDays) {
        this.thresholdDays = thresholdDays;
    }

    @Override
    public Optional<String> analyze(Product product, SalesData salesData, LocalDate referenceDate) {
        long shelfDays = ChronoUnit.DAYS.between(product.getDateAdded(), referenceDate);
        int sold = salesData.getSoldSince(product.getSku(), product.getDateAdded());

        if (shelfDays >= thresholdDays && sold == 0) {
            return Optional.of("Model '" + product.getModel() +
                    "' (SKU: " + product.getSku() + ") has been on shelf for " +
                    shelfDays + " days with 0 sales.");
        }

        return Optional.empty();
    }
}
