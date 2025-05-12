import java.time.LocalDate;
import java.util.*;

public class Inventory {

    private List<Product>products;
    public Inventory(List<Product> products)
    {
        this.products=products;
    }
    public List<Product>getAllByBrand(String brand)
    {
        List<Product> filtered = new ArrayList<>();
        for (Product p:products)
        {
            if (p.getBrand().equalsIgnoreCase(brand))
            {
                filtered.add(p);
            }
        }
        return filtered;
    }

    public List<Product> getBrandSalesWithinPeriod(String brand, int daysBack, SalesData salesData) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand must not be empty");
        }

        if (daysBack < 0) {
            throw new IllegalArgumentException("daysBack must be non-negative");
        }

        LocalDate threshold = LocalDate.now().minusDays(daysBack);

        return products.stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                .filter(p -> salesData.getSoldSince(p.getSku(), threshold) > 0)
                .sorted(
                        Comparator
                                .comparingInt((Product p) -> salesData.getSoldSince(p.getSku(), threshold))
                                .reversed()
                                .thenComparing(Product::getModel)
                                .thenComparing(Product::getSku)
                )
                .toList();
    }

    public List<Product>getUnsoldProductsWithinPeriod(int daysBack, SalesData salesData)
    {

        LocalDate threshold=LocalDate.now().minusDays(daysBack);
        return products.stream().filter
                        (p->salesData.getSoldSince(p.getSku(),threshold)==0).
                sorted(Comparator.comparing(Product::getBrand).thenComparing(Product::getModel)).toList();
    }

    public List<Product> getTopSellingProducts(int topN, int daysBack, SalesData salesData, Optional<String> brandFilter) {
        if (topN <= 0) {
            throw new IllegalArgumentException("topN must be greater than 0");
        }

        if (daysBack < 0) {
            throw new IllegalArgumentException("daysBack must be non-negative");
        }

        LocalDate threshold = LocalDate.now().minusDays(daysBack);

        return products.stream()
                .filter(p -> brandFilter.map(b -> p.getBrand().equalsIgnoreCase(b)).orElse(true))
                .filter(p -> salesData.getSoldSince(p.getSku(), threshold) > 0)
                .sorted(
                        Comparator.comparingInt((Product p) -> salesData.getSoldSince(p.getSku(), threshold))
                                .reversed().thenComparing(Product::getModel).thenComparing(Product::getSku)
                ).limit(topN).toList();
    }
};
