import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Sipariş bilgilerini tutan Java sınıfı
 * OOP prensipleri ile geliştirilmiştir
 */
public class Order {
    private int id;
    private int customerId;
    private String customerName;
    private int productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double total;
    private OrderStatus status;
    private String shippingCompany;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Enum - Sipariş Durumu
    public enum OrderStatus {
        PREPARING,  // Hazırlanıyor
        SHIPPING,   // Sevkiyatta
        DELIVERED   // Teslim Edildi
    }

    // Constructor
    public Order(int id, int customerId, String customerName, int productId, 
                String productName, int quantity, double unitPrice, double total) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.status = OrderStatus.PREPARING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Constructor - Tam
    public Order(int id, int customerId, String customerName, int productId, 
                String productName, int quantity, double unitPrice, double total,
                String shippingCompany, String note) {
        this(id, customerId, customerName, productId, productName, quantity, unitPrice, total);
        this.shippingCompany = shippingCompany;
        this.note = note;
    }

    // Getter ve Setter metodları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // İş mantığı metodları
    public String getFormattedCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return createdAt.format(formatter);
    }

    public String getStatusString() {
        switch (status) {
            case PREPARING: return "Hazırlanıyor";
            case SHIPPING: return "Sevkiyatta";
            case DELIVERED: return "Teslim Edildi";
            default: return "Bilinmiyor";
        }
    }

    public void moveToShipping() {
        if (status == OrderStatus.PREPARING) {
            this.status = OrderStatus.SHIPPING;
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void markAsDelivered() {
        if (status == OrderStatus.SHIPPING) {
            this.status = OrderStatus.DELIVERED;
            this.updatedAt = LocalDateTime.now();
        }
    }

    public boolean isDelivered() {
        return status == OrderStatus.DELIVERED;
    }

    public boolean isPreparing() {
        return status == OrderStatus.PREPARING;
    }

    public boolean isShipping() {
        return status == OrderStatus.SHIPPING;
    }

    public String generateInvoice() {
        StringBuilder invoice = new StringBuilder();
        invoice.append("═══════════════════════════════════════\n");
        invoice.append("        YÜCEL ORMAN ÜRÜNLERİ\n");
        invoice.append("           FATURA\n");
        invoice.append("═══════════════════════════════════════\n\n");
        invoice.append("Fatura No: INV-").append(id).append("\n");
        invoice.append("Tarih: ").append(getFormattedCreatedDate()).append("\n\n");
        invoice.append("───────────────────────────────────────\n");
        invoice.append("MÜŞTERİ BİLGİLERİ\n");
        invoice.append("───────────────────────────────────────\n");
        invoice.append("Müşteri: ").append(customerName).append("\n\n");
        invoice.append("───────────────────────────────────────\n");
        invoice.append("ÜRÜN BİLGİLERİ\n");
        invoice.append("───────────────────────────────────────\n");
        invoice.append("Ürün: ").append(productName).append("\n");
        invoice.append("Miktar: ").append(quantity).append("\n");
        invoice.append("Birim Fiyat: ").append(String.format("%.2f", unitPrice)).append(" TL\n\n");
        invoice.append("───────────────────────────────────────\n");
        invoice.append("TOPLAM: ").append(String.format("%.2f", total)).append(" TL\n");
        invoice.append("───────────────────────────────────────\n\n");
        if (shippingCompany != null && !shippingCompany.isEmpty()) {
            invoice.append("Nakliye: ").append(shippingCompany).append("\n\n");
        }
        invoice.append("═══════════════════════════════════════\n");
        invoice.append("İletişim: FUAT YÜCEL - 0506 471 56 75\n");
        invoice.append("═══════════════════════════════════════\n");
        
        return invoice.toString();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", total=" + total +
                ", status=" + status +
                ", createdAt=" + getFormattedCreatedDate() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}