import java.util.ArrayList;
import java.util.List;

/**
 * Müşteri bilgilerini tutan Java sınıfı
 * OOP prensipleri ile geliştirilmiştir
 * Bireysel ve Kurumsal müşteri desteği
 */
public class Customer {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private double debt;
    private CustomerType type; // Bireysel veya Kurumsal
    private double discountRate; // Özel fiyat indirimi (bayiler için)
    private List<Integer> orderHistory; // Sipariş geçmişi
    private boolean isActive;

    // Enum - Müşteri Tipi
    public enum CustomerType {
        INDIVIDUAL, // Bireysel
        CORPORATE   // Kurumsal
    }

    // Constructor - Temel
    public Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.debt = 0.0;
        this.type = CustomerType.INDIVIDUAL;
        this.discountRate = 0.0;
        this.orderHistory = new ArrayList<>();
        this.isActive = true;
    }

    // Constructor - Tam
    public Customer(int id, String name, String phone, String email, String address, 
                   double debt, CustomerType type, double discountRate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.debt = debt;
        this.type = type;
        this.discountRate = discountRate;
        this.orderHistory = new ArrayList<>();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        if (debt >= 0) {
            this.debt = debt;
        }
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        if (discountRate >= 0 && discountRate <= 100) {
            this.discountRate = discountRate;
        }
    }

    public List<Integer> getOrderHistory() {
        return orderHistory;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // İş mantığı metodları
    public void addDebt(double amount) {
        if (amount > 0) {
            this.debt += amount;
        }
    }

    public boolean payDebt(double amount) {
        if (amount > 0 && this.debt >= amount) {
            this.debt -= amount;
            return true;
        }
        return false;
    }

    public boolean hasDebt() {
        return this.debt > 0;
    }

    public void addOrder(int orderId) {
        this.orderHistory.add(orderId);
    }

    public int getTotalOrders() {
        return this.orderHistory.size();
    }

    public double calculateDiscountedPrice(double originalPrice) {
        if (this.discountRate > 0) {
            return originalPrice * (1 - this.discountRate / 100);
        }
        return originalPrice;
    }

    public boolean isCorporate() {
        return this.type == CustomerType.CORPORATE;
    }

    public String getTypeString() {
        return this.type == CustomerType.INDIVIDUAL ? "Bireysel" : "Kurumsal";
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", debt=" + debt +
                ", type=" + type +
                ", discountRate=" + discountRate +
                ", totalOrders=" + orderHistory.size() +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}