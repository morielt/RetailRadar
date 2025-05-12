import java.time.LocalDate;
import java.util.Optional;


public interface InsightStrategy {
    Optional<String> analyze(Product product, SalesData salesData, LocalDate referenceDate);
}
