class Equipment {
    private String id;
    private String name;
    private int availableCount;

    public Equipment(String id, String name, int availableCount) {
        if (id == null || id.trim().isEmpty()) {
            this.id = "Unknown";
        } else {
            this.id = id;
        }

        if (name == null || name.trim().isEmpty()) {
            this.name = "Unknown";
        } else {
            this.name = name;
        }

        if (availableCount < 0) {
            this.availableCount = 0;
        } else {
            this.availableCount = availableCount;
        }
    }

    public boolean borrowOne() {
        if (this.availableCount > 0) {
            this.availableCount--;
            return true;
        } else {
            return false;
        }
    }

    public void returnItems(int quantity) {
        if (quantity > 0) {
            this.availableCount += quantity;
        }
    }

    @Override
    public String toString() {
        return "設備編號：" + id + " | 名稱：" + name + " | 可借數量：" + availableCount;
    }
}

public class EquipmentInventory {
    public static void main(String[] args) {
        Equipment eq1 = new Equipment("EQ001", "投影機", 1);
        Equipment eq2 = new Equipment("", "  ", -5);

        System.out.println("【初始狀態】");
        System.out.println(eq1);
        System.out.println(eq2);

        System.out.println("\n【測試借用功能】");
        boolean result1 = eq1.borrowOne();
        System.out.println("eq1 第一次借用：" + (result1 ? "成功" : "失敗"));
        System.out.println(eq1);

        boolean result2 = eq1.borrowOne();
        System.out.println("eq1 第二次借用：" + (result2 ? "成功" : "失敗"));
        System.out.println(eq1);

        System.out.println("\n【測試歸還功能】");
        eq1.returnItems(-3);
        System.out.println("eq1 嘗試歸還 -3 個後：" + eq1);

        eq1.returnItems(2);
        System.out.println("eq1 正常歸還 2 個後：" + eq1);
    }
}