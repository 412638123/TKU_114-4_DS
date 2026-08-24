import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListImplementationLab {

    public static void testListOperations(List<Integer> list) {
        list.clear();

        // 1. 尾端新增
        for (int i = 1; i <= 5; i++) {
            list.add(i * 10);
        }
        System.out.println("尾端新增後: " + list);

        // 2. 指定位置插入 (在 index 2 插入 25)
        list.add(2, 25);
        System.out.println("在 index 2 插入 25 後: " + list);

        // 3. 搜尋 (搜尋元素 30 的 index)
        int target = 30;
        int searchIndex = list.indexOf(target);
        System.out.println("搜尋 " + target + " 的 index: " + searchIndex);

        // 4. 刪除 (刪除 index 1 的元素)
        int removedValue = list.remove(1);
        System.out.println("刪除 index 1 的元素 (" + removedValue + ") 後: " + list);

        // 5. 總和計算
        long sum = 0;
        for (int num : list) {
            sum += num;
        }
        System.out.println("所有元素總和: " + sum);
    }

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        System.out.println("=== 1. 測試 ArrayList 實作 ===");
        testListOperations(arrayList);

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 2. 測試 LinkedList 實作 ===");
        testListOperations(linkedList);

        System.out.println("\n========================================");
        System.out.println("【ArrayList vs LinkedList 內部成本差異說明】");
        System.out.println("========================================");
        System.out.println("1. 尾端新增 (add):");
        System.out.println("   - ArrayList：均攤時間複雜度 O(1)。內部為動態陣列，若空間不足需進行擴容（通常為 1.5 倍）與陣列複製。");
        System.out.println("   - LinkedList：時間複雜度 O(1)。直接將尾端指標指向新建立的 Node，不需擴容，但有額外 Node 物件記憶體開銷。");

        System.out.println("\n2. 指定位置插入 (add at index):");
        System.out.println("   - ArrayList：需要將指定位置後方的所有元素整體向後位移 (System.arraycopy)，平均成本為 O(n)。");
        System.out.println("   - LinkedList：需要先從頭/尾指標循序 traversal 走訪到指定 index (O(n))，找到位置後調整指標連結只需要 O(1)。");

        System.out.println("\n3. 搜尋與隨機存取 (indexOf / get):");
        System.out.println("   - ArrayList：基於陣列連續記憶體位址，隨機存取 get(i) 為 O(1)；indexOf 搜尋需線性掃描，為 O(n)。");
        System.out.println("   - LinkedList：無連續位址，隨機存取與搜尋均需從頭或尾節點開始走訪 Node，成本皆為 O(n)。");

        System.out.println("\n4. 刪除 (remove at index):");
        System.out.println("   - ArrayList：刪除後需將後方元素整體向前位移填補空缺，成本為 O(n)。");
        System.out.println("   - LinkedList：找到目標 Node 需 O(n) 走訪時間，調整斷開與重新連接指標只需 O(1)。");

        System.out.println("\n5. 總和與走訪 (Iteration):");
        System.out.println("   - ArrayList：快取友善度 (Cache Locality) 高，連續記憶體區塊讓 CPU 快取命中率極高，走訪速度最快。");
        System.out.println("   - LinkedList：Node 物件分散在 Heap 各處，CPU 快取容易 Miss，走訪效能較 ArrayList 差。");
    }
}