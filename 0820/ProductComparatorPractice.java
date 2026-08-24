import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class StoreProduct implements Comparable<StoreProduct> {
    private String id;
    private String name;
    private double price;
    private int stock;

    public StoreProduct(String id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price < 0 ? 0 : price;
        this.stock = stock < 0 ? 0 : stock;
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

    public int getStock() {
        return stock;
    }

    @Override
    public int compareTo(StoreProduct other) {
        if (other == null) return 1;
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return String.format("ID: %-5s | 名稱: %-8s | 價格: %6.1f | 庫存: %3d", id, name, price, stock);
    }
}

public class ProductComparatorPractice {
    public static void main(String[] args) {
        List<StoreProduct> originalList = new ArrayList<>();
        originalList.add(new StoreProduct("P003", "Mouse", 500.0, 50));
        originalList.add(new StoreProduct("P001", "Keyboard", 1500.0, 20));
        originalList.add(new StoreProduct("P005", "Monitor", 1500.0, 10));
        originalList.add(new StoreProduct("P002", "Headset", 800.0, 20));
        originalList.add(new StoreProduct("P004", "Webcam", 500.0, 35));

        System.out.println("=== 原始商品清單 ===");
        printList(originalList);

        System.out.println("\n=== 1. Natural Order 排序 (依 ID 升冪) ===");
        List<StoreProduct> list1 = new ArrayList<>(originalList);
        Collections.sort(list1);
        printList(list1);

        System.out.println("\n=== 2. Comparator 一 (依 價格升冪，同價時依 名稱) ===");
        List<StoreProduct> list2 = new ArrayList<>(originalList);
        list2.sort(Comparator.comparingDouble(StoreProduct::getPrice)
                             .thenComparing(StoreProduct::getName));
        printList(list2);

        System.out.println("\n=== 3. Comparator 二 (依 庫存降冪，同庫存時依 ID) ===");
        List<StoreProduct> list3 = new ArrayList<>(originalList);
        list3.sort(Comparator.comparingInt(StoreProduct::getStock).reversed()
                             .thenComparing(StoreProduct::getId));
        printList(list3);

        System.out.println("\n=== 驗證原始清單是否未受修改 ===");
        printList(originalList);
    }

    private static void printList(List<StoreProduct> products) {
        for (StoreProduct p : products) {
            System.out.println(p);
        }
    }
}