/**
 * Ürün bilgilerini tutan Java sınıfı
 * OOP prensipleri ile geliştirilmiştir
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private String unit; // Birim: Adet, m³, Ton, Metre, Kg
    private int criticalStock; // Kritik stok seviyesi
    private String category;
    private boolean isActive;

    // Constructor - Temel
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = 0;
        this.unit = "Adet";
        this.criticalStock = 10;
        this.isActive = true;
    }

    // Constructor - Tam
    public Product(int id, String name, double price, int stock, String unit, int criticalStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.unit = unit;
        this.criticalStock = criticalStock;
        this.isActive = true;
    }

    // Getter ve Setter metodları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        }
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        }
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getCriticalStock() {
        return criticalStock;
    }

    public void setCriticalStock(int criticalStock) {
        this.criticalStock = criticalStock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // İş mantığı metodları
    public void addStock(int quantity) {
        if (quantity > 0) {
            this.stock += quantity;
        }
    }

    public boolean subtractStock(int quantity) {
        if (quantity > 0 && this.stock >= quantity) {
            this.stock -= quantity;
            return true;
        }
        return false;
    }

    public boolean isCriticalStock() {
        return this.stock <= this.criticalStock;
    }

    public boolean isOutOfStock() {
        return this.stock == 0;
    }

    public double calculateTotalValue() {
        return this.price * this.stock;
    }

    public void updatePrice(double newPrice) {
        if (newPrice >= 0) {
            this.price = newPrice;
        }
    }

    public void applyDiscount(double percentage) {
        if (percentage > 0 && percentage <= 100) {
            this.price = this.price * (1 - percentage / 100);
        }
    }

    public void increasePrice(double percentage) {
        if (percentage > 0) {
            this.price = this.price * (1 + percentage / 100);
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", unit='" + unit + '\'' +
                ", criticalStock=" + criticalStock +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}