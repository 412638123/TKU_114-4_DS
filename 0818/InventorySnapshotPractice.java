import java.util.Arrays;

final class InventorySnapshot {
    private final String warehouseId;
    private final int[] quantities;

    public InventorySnapshot(String warehouseId, int[] quantities) {
        this.warehouseId = warehouseId;
        if (quantities == null) {
            this.quantities = new int[0];
        } else {
            this.quantities = quantities.clone();
        }
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public int[] getQuantities() {
        return quantities.clone();
    }

    public int totalQuantity() {
        int total = 0;
        for (int q : quantities) {
            total += q;
        }
        return total;
    }

    public int outOfStockCount() {
        int count = 0;
        for (int q : quantities) {
            if (q == 0) {
                count++;
            }
        }
        return count;
    }
}

public class InventorySnapshotPractice {
    public static void main(String[] args) {
        int[] testQuantities = {5, 0, 3, 0};
        InventorySnapshot snapshot = new InventorySnapshot("WH001", testQuantities);

        System.out.println("倉庫編號：" + snapshot.getWarehouseId());
        System.out.println("總數量：" + snapshot.totalQuantity());
        System.out.println("缺貨品項數：" + snapshot.outOfStockCount());

        InventorySnapshot nullSnapshot = new InventorySnapshot("WH002", null);
        System.out.println("\nNull 陣列測試總數量：" + nullSnapshot.totalQuantity());
    }
}