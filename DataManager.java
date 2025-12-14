import java.util.*;
import java.io.*;

/**
 * Veri yönetimi için Java sınıfı
 * OOP Prensipleri: Singleton Pattern, Data Persistence
 */
public class DataManager {
    private static DataManager instance;
    
    private Map<Integer, Product> products;
    private Map<Integer, Customer> customers;
    private List<Transaction> transactions;
    private int nextProductId;
    private int nextCustomerId;
    private int nextTransactionId;
    
    // Kritik stok uyarıları için liste
    private List<String> stockAlerts;

    // Private constructor (Singleton)
    private DataManager() {
        this.products = new HashMap<>();
        this.customers = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.stockAlerts = new ArrayList<>();
        this.nextProductId = 1;
        this.nextCustomerId = 1;
        this.nextTransactionId = 1;
        initializeDefaultData();
    }

    // Singleton instance
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    // Varsayılan verileri yükle
    private void initializeDefaultData() {
        // Örnek ürünler
        addProduct("Kereste (Çam)", "Ahşap", 850.0, 100, "m³", 20, 650.0);
        addProduct("Kereste (Meşe)", "Ahşap", 1200.0, 75, "m³", 15, 900.0);
        addProduct("Kontrplak 18mm", "Panel", 450.0, 200, "adet", 30, 320.0);
        addProduct("MDF 16mm", "Panel", 380.0, 150, "adet", 25, 280.0);
        addProduct("Lamine Parke", "Döşeme", 95.0, 500, "m²", 50, 65.0);
        
        // Örnek müşteriler
        addCustomer("Ahmet Yılmaz", "05321234567", "ahmet@email.com", "İstanbul", "Bireysel", 0.0);
        addCustomer("Mobilya A.Ş.", "02121234567", "info@mobilya.com", "Ankara", "Kurumsal", 15.0);
        addCustomer("Yapı Market", "02161234567", "satis@yapimarket.com", "İzmir", "Kurumsal", 20.0);
    }

    // ==================== ÜRÜN YÖNETİMİ ====================
    
    public Product addProduct(String name, String category, double price, int stock, 
                             String unit, int criticalLevel, double costPrice) {
        Product product = new Product(nextProductId++, name, category, price, stock, 
                                     unit, criticalLevel, costPrice);
        products.put(product.getId(), product);
        checkStockAlert(product);
        return product;
    }

    public boolean updateProduct(int id, String name, String category, double price, 
                                String unit, int criticalLevel, double costPrice) {
        Product product = products.get(id);
        if (product != null) {
            product.setName(name);
            product.setCategory(category);
            product.setPrice(price);
            product.setUnit(unit);
            product.setCriticalStockLevel(criticalLevel);
            product.setCostPrice(costPrice);
            return true;
        }
        return false;
    }

    public boolean updateProductPrice(int id, double newPrice) {
        Product product = products.get(id);
        if (product != null) {
            product.setPrice(newPrice);
            return true;
        }
        return false;
    }

    public boolean updateProductStock(int id, int newStock) {
        Product product = products.get(id);
        if (product != null) {
            product.setStock(newStock);
            checkStockAlert(product);
            return true;
        }
        return false;
    }

    public boolean deleteProduct(int id) {
        Product product = products.remove(id);
        return product != null;
    }

    public Product getProduct(int id) {
        return products.get(id);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public List<Product> getActiveProducts() {
        List<Product> activeProducts = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.isActive()) {
                activeProducts.add(p);
            }
        }
        return activeProducts;
    }

    public List<Product> getCriticalStockProducts() {
        List<Product> criticalProducts = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.isCriticalStock()) {
                criticalProducts.add(p);
            }
        }
        return criticalProducts;
    }

    // Toplu fiyat güncelleme
    public void bulkUpdatePrices(double percentage) {
        for (Product p : products.values()) {
            double newPrice = p.getPrice() * (1 + percentage / 100.0);
            p.setPrice(newPrice);
        }
    }

    // Kritik stok kontrolü
    private void checkStockAlert(Product product) {
        if (product.isCriticalStock()) {
            String alert = String.format("⚠️ UYARI: %s ürününün stoğu kritik seviyede! (Mevcut: %d %s, Kritik: %d)",
                    product.getName(), product.getStock(), product.getUnit(), product.getCriticalStockLevel());
            stockAlerts.add(alert);
        }
    }

    public List<String> getStockAlerts() {
        return new ArrayList<>(stockAlerts);
    }

    public void clearStockAlerts() {
        stockAlerts.clear();
    }

    // ==================== MÜŞTERİ YÖNETİMİ ====================
    
    public Customer addCustomer(String name, String phone, String email, String address,
                               String customerType, double specialDiscount) {
        Customer customer = new Customer(nextCustomerId++, name, phone, email, address,
                                        customerType, 0.0, specialDiscount);
        customers.put(customer.getId(), customer);
        return customer;
    }

    public boolean updateCustomer(int id, String name, String phone, String email,
                                 String address, String customerType, double specialDiscount) {
        Customer customer = customers.get(id);
        if (customer != null) {
            customer.setName(name);
            customer.setPhone(phone);
            customer.setEmail(email);
            customer.setAddress(address);
            customer.setCustomerType(customerType);
            customer.setSpecialDiscount(specialDiscount);
            return true;
        }
        return false;
    }

    public boolean deleteCustomer(int id) {
        Customer customer = customers.remove(id);
        return customer != null;
    }

    public Customer getCustomer(int id) {
        return customers.get(id);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    public List<Transaction> getCustomerOrderHistory(int customerId) {
        List<Transaction> customerOrders = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getCustomerId() == customerId) {
                customerOrders.add(t);
            }
        }
        return customerOrders;
    }

    // ==================== SİPARİŞ YÖNETİMİ ====================
    
    public Transaction createTransaction(String type, int customerId, int productId,
                                        int quantity, double discount) {
        Customer customer = customers.get(customerId);
        Product product = products.get(productId);
        
        if (customer == null || product == null) {
            return null;
        }

        // Satış ise stok kontrolü
        if (type.equals("Satış") && product.getStock() < quantity) {
            return null;
        }

        // Özel fiyat hesaplama (bayi indirimi)
        double finalPrice = customer.calculateSpecialPrice(product.getPrice());
        
        Transaction transaction = new Transaction(
            nextTransactionId++, type, customerId, customer.getName(),
            productId, product.getName(), quantity, product.getUnit(),
            finalPrice, discount
        );

        // Stok güncelleme
        if (type.equals("Satış")) {
            product.removeStock(quantity);
            customer.addDebt(transaction.getTotal());
        } else if (type.equals("Alım")) {
            product.addStock(quantity);
        }

        transactions.add(transaction);
        customer.addOrder(transaction.getId());
        checkStockAlert(product);
        
        return transaction;
    }

    public boolean updateTransactionStatus(int transactionId, String newStatus) {
        for (Transaction t : transactions) {
            if (t.getId() == transactionId) {
                t.setStatus(newStatus);
                return true;
            }
        }
        return false;
    }

    public boolean setShippingCompany(int transactionId, String company) {
        for (Transaction t : transactions) {
            if (t.getId() == transactionId) {
                t.setShippingCompany(company);
                return true;
            }
        }
        return false;
    }

    public Transaction getTransaction(int id) {
        for (Transaction t : transactions) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    public List<Transaction> getTransactionsByStatus(String status) {
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getStatus().equals(status)) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    // ==================== RAPORLAMA ====================
    
    // En çok satan ürünler
    public Map<String, Integer> getTopSellingProducts(int limit) {
        Map<String, Integer> productSales = new HashMap<>();
        
        for (Transaction t : transactions) {
            if (t.getType().equals("Satış")) {
                String productName = t.getProductName();
                productSales.put(productName, 
                    productSales.getOrDefault(productName, 0) + t.getQuantity());
            }
        }
        
        return productSales;
    }

    // Günlük satış raporu
    public double getDailySales(Date date) {
        double total = 0.0;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        
        for (Transaction t : transactions) {
            if (t.getType().equals("Satış")) {
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(t.getDate());
                
                if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                    total += t.getTotal();
                }
            }
        }
        return total;
    }

    // Aylık satış raporu
    public double getMonthlySales(int year, int month) {
        double total = 0.0;
        
        for (Transaction t : transactions) {
            if (t.getType().equals("Satış")) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(t.getDate());
                
                if (cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) == month) {
                    total += t.getTotal();
                }
            }
        }
        return total;
    }

    // Kâr-Zarar analizi
    public Map<String, Double> getProfitLossAnalysis() {
        Map<String, Double> analysis = new HashMap<>();
        double totalRevenue = 0.0;
        double totalCost = 0.0;
        double totalProfit = 0.0;
        
        for (Transaction t : transactions) {
            if (t.getType().equals("Satış")) {
                Product product = products.get(t.getProductId());
                if (product != null) {
                    double revenue = t.getTotal();
                    double cost = product.getCostPrice() * t.getQuantity();
                    double profit = revenue - cost;
                    
                    totalRevenue += revenue;
                    totalCost += cost;
                    totalProfit += profit;
                }
            }
        }
        
        analysis.put("revenue", totalRevenue);
        analysis.put("cost", totalCost);
        analysis.put("profit", totalProfit);
        analysis.put("margin", totalRevenue > 0 ? (totalProfit / totalRevenue * 100) : 0);
        
        return analysis;
    }

    // Stok tüketim raporu
    public Map<String, Integer> getStockConsumptionReport() {
        Map<String, Integer> consumption = new HashMap<>();
        
        for (Transaction t : transactions) {
            if (t.getType().equals("Satış")) {
                String productName = t.getProductName();
                consumption.put(productName,
                    consumption.getOrDefault(productName, 0) + t.getQuantity());
            }
        }
        
        return consumption;
    }

    // Toplam stok değeri
    public double getTotalStockValue() {
        double total = 0.0;
        for (Product p : products.values()) {
            total += p.getStockValue();
        }
        return total;
    }
}