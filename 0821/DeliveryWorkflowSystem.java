import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

class DeliveryPackage {
    private final String id;
    private final String address;
    private String status;

    public DeliveryPackage(String id, String address) {
        this.id = id;
        this.address = address;
        this.status = "PENDING";
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("包裹編號: %-6s | 目的地址: %-15s | 狀態: %s", id, address, status);
    }
}

public class DeliveryWorkflowSystem {
    private final Map<String, DeliveryPackage> packageMap = new HashMap<>();
    private final Deque<DeliveryPackage> pendingQueue = new ArrayDeque<>();
    private final Deque<DeliveryPackage> completedStack = new ArrayDeque<>();


    public boolean addPackage(String id, String address) {
        if (id == null || id.trim().isEmpty() || address == null || address.trim().isEmpty()) {
            System.out.println("新增失敗：資料不完整");
            return false;
        }
        String cleanId = id.trim();
        if (packageMap.containsKey(cleanId)) {
            System.out.println("新增失敗：包裹編號 " + cleanId + " 已存在");
            return false;
        }

        DeliveryPackage pkg = new DeliveryPackage(cleanId, address.trim());
        packageMap.put(cleanId, pkg);
        pendingQueue.offerLast(pkg);
        System.out.println("成功新增包裹：" + pkg);
        return true;
    }


    public DeliveryPackage processNextDelivery() {
        if (pendingQueue.isEmpty()) {
            System.out.println("處理失敗：當前無等待配送的包裹");
            return null;
        }

        DeliveryPackage pkg = pendingQueue.pollFirst();
        pkg.setStatus("DELIVERED");
        completedStack.push(pkg);
        System.out.println("成功完成配送：" + pkg);
        return pkg;
    }


    public DeliveryPackage undoLastDelivery() {
        if (completedStack.isEmpty()) {
            System.out.println("Undo 失敗：當前無已完成的配送紀錄");
            return null;
        }

        DeliveryPackage pkg = completedStack.pop();
        pkg.setStatus("PENDING");
        pendingQueue.offerFirst(pkg);
        System.out.println("成功撤銷配送，包裹已退回等待佇列：" + pkg);
        return pkg;
    }


    public DeliveryPackage findById(String id) {
        if (id == null) return null;
        return packageMap.get(id.trim());
    }


    public void printReport() {
        int pendingCount = 0;
        int deliveredCount = 0;

        for (DeliveryPackage pkg : packageMap.values()) {
            if ("DELIVERED".equals(pkg.getStatus())) {
                deliveredCount++;
            } else {
                pendingCount++;
            }
        }

        System.out.println("\n=========================================");
        System.out.println("【物流工作流程統計報告】");
        System.out.println("-----------------------------------------");
        System.out.println("系統總包裹數量 : " + packageMap.size());
        System.out.println("等待配送數量 (Queue): " + pendingCount);
        System.out.println("已完成配送數量 (Stack): " + deliveredCount);
        System.out.println("=========================================");
    }

    public static void main(String[] args) {
        DeliveryWorkflowSystem system = new DeliveryWorkflowSystem();

        System.out.println("=== 1. 新增包裹測試 (含重複 ID 阻擋) ===");
        system.addPackage("PKG01", "台北市信義區路一段1號");
        system.addPackage("PKG02", "台中市西屯區大道二段2號");
        system.addPackage("PKG03", "高雄市新興區中正三路3號");
        system.addPackage("PKG01", "重複的編號測試");

        system.printReport();

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 2. 依編號查詢測試 (Map 快速搜尋) ===");
        System.out.println("查詢 PKG02: " + system.findById("PKG02"));
        System.out.println("查詢 PKG99 (不存在): " + system.findById("PKG99"));

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 3. 處理配送測試 (Queue FIFO 順序) ===");
        system.processNextDelivery();
        system.processNextDelivery();

        system.printReport();

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 4. Undo 撤銷配送測試 (Stack LIFO 復原) ===");
        system.undoLastDelivery();

        system.printReport();

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 5. 繼續處理剩餘配送 ===");
        system.processNextDelivery();
        system.processNextDelivery();
        system.processNextDelivery();

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 6. 邊界測試：無可 Undo 紀錄 ===");
        system.undoLastDelivery();
        system.undoLastDelivery();
        system.undoLastDelivery();
        system.undoLastDelivery();

        system.printReport();
    }
}