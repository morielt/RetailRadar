import java.time.LocalDate;

public class SaleRecord {

    String sku;
    LocalDate dateSold;
    int quantity;

    public SaleRecord(String sku, LocalDate dateSold, int quantity) {
        this.sku = sku;
        this.dateSold = dateSold;
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public LocalDate getDateSold() {
        return dateSold;
    }

    public int getQuantity() {
        return quantity;
    }
}
