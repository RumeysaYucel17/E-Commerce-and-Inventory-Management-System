import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Satış/Alım işlemlerini tutan Java sınıfı
 * OOP prensipleri ile geliştirilmiştir
 */
public class Transaction {
    private int id;
    private TransactionType type;
    private int customerId;
    private String customerName;
    private int productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double total;
    private LocalDateTime date;
    private String note;
    private TransactionStatus status;

    // Enum - İşlem Tipi
    public enum TransactionType {
        SALE,     // Satış
        PURCHASE  // Alım
    }

    // Enum - İşlem Durumu
    public enum TransactionStatus {
        COMPLETED,  // Tamamlandı
        PENDING,    // Beklemede
        CANCELLED   // İptal Edildi
    }

    // Constructor
    public Transaction(int id, TransactionType type, int customerId, String customerName, 
                      int productId, String productName, int quantity, 
                      double unitPrice, double total) {
        this.id = id;
        this.type = type;
        this.customerId = customerId;
        this.customerName = customerName;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.date = LocalDateTime.now();
        this.status = TransactionStatus.COMPLETED;
    }

    // Constructor - Tam
    public Transaction(int id, TransactionType type, int customerId, String customerName, 
                      int productId, String productName, int quantity, 
                      double unitPrice, double total, String note) {
        this(id, type, customerId, customerName, productId, productName, quantity, unitPrice, total);
        this.note = note;
    }

    // Getter ve Setter metodları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    // İş mantığı metodları
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return date.format(formatter);
    }

    public String getTypeString() {
        return type == TransactionType.SALE ? "Satış" : "Alım";
    }

    public String getStatusString() {
        switch (status) {
            case COMPLETED: return "Tamamlandı";
            case PENDING: return "Beklemede";
            case CANCELLED: return "İptal Edildi";
            default: return "Bilinmiyor";
        }
    }

    public boolean isSale() {
        return type == TransactionType.SALE;
    }

    public boolean isPurchase() {
        return type == TransactionType.PURCHASE;
    }

    public void cancel() {
        this.status = TransactionStatus.CANCELLED;
    }

    public boolean isCompleted() {
        return status == TransactionStatus.COMPLETED;
    }

    public void recalculateTotal() {
        this.total = this.unitPrice * this.quantity;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", customerName='" + customerName + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                ", date=" + getFormattedDate() +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Transaction that = (Transaction) obj;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}