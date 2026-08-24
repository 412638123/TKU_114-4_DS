import java.util.ArrayList;
import java.util.List;

class Repository<T> {
    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        if (item != null) {
            items.add(item);
        }
    }

    public T get(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    public boolean remove(T item) {
        return items.remove(item);
    }

    public int size() {
        return items.size();
    }

    public void printAll() {
        if (items.isEmpty()) {
            System.out.println("(資料庫為空)");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.println("[" + i + "] " + items.get(i));
        }
    }
}

class Product {
    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price < 0 ? 0 : price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return id != null ? id.equals(product.id) : product.id == null;
    }

    @Override
    public String toString() {
        return "商品 ID: " + id + " | 名稱: " + name + " | 價格: " + price;
    }
}

public class GenericRepositorySystem {
    public static void main(String[] args) {
        System.out.println("=== 1. Repository<String> 測試 ===");
        Repository<String> stringRepo = new Repository<>();
        stringRepo.add("Java");
        stringRepo.add("Python");
        stringRepo.add("C++");

        System.out.println("初始內容 (size: " + stringRepo.size() + ")：");
        stringRepo.printAll();

        System.out.println("\n取得 Index 1 內容：" + stringRepo.get(1));

        System.out.println("\n移除 \"Python\" 項目：" + (stringRepo.remove("Python") ? "成功" : "失敗"));
        System.out.println("移除後內容 (size: " + stringRepo.size() + ")：");
        stringRepo.printAll();

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 2. Repository<Product> 測試 ===");
        Repository<Product> productRepo = new Repository<>();
        Product p1 = new Product("P001", "滑鼠", 590);
        Product p2 = new Product("P002", "鍵盤", 1290);
        Product p3 = new Product("P003", "螢幕", 4500);

        productRepo.add(p1);
        productRepo.add(p2);
        productRepo.add(p3);

        System.out.println("初始商品列表 (size: " + productRepo.size() + ")：");
        productRepo.printAll();

        System.out.println("\n取得 Index 0 商品：" + productRepo.get(0));

        System.out.println("\n移除商品 P002 (鍵盤)：" + (productRepo.remove(p2) ? "成功" : "失敗"));
        System.out.println("移除後商品列表 (size: " + productRepo.size() + ")：");
        productRepo.printAll();

        System.out.println("\n【邊界條件測試】");
        System.out.println("取得不合法索引 (Index 10)：" + productRepo.get(10));
        System.out.println("移除不存在的商品：" + (productRepo.remove(new Product("P999", "不存在", 0)) ? "成功" : "失敗"));
    }
}