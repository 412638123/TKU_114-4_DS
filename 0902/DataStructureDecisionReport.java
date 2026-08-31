import java.util.ArrayList;
import java.util.List;

public class DataStructureDecisionReport {

    public static class ScenarioDecision {
        private int id;
        private String scenario;
        private String chosenDataStructure;
        private String primaryBigO;
        private String justification;

        public ScenarioDecision(int id, String scenario, String chosenDataStructure, String primaryBigO, String justification) {
            this.id = id;
            this.scenario = scenario;
            this.chosenDataStructure = chosenDataStructure;
            this.primaryBigO = primaryBigO;
            this.justification = justification;
        }

        @Override
        public String toString() {
            return String.format("[%2d] %s\n     Selection: %s | Big-O: %s\n     Reason: %s\n",
                    id, scenario, chosenDataStructure, primaryBigO, justification);
        }
    }

    public static List<ScenarioDecision> generateDecisionReport() {
        List<ScenarioDecision> decisions = new ArrayList<>();

        decisions.add(new ScenarioDecision(1, "高頻率依 Key 查詢與更新資料", "HashMap / HashTable", "O(1) Avg", "哈希表能在常數時間內完成 key 的查找與更新，極度適合高頻隨機存取。"));
        decisions.add(new ScenarioDecision(2, "資料需動態保持排序狀態並進行範圍搜尋", "TreeMap (Red-Black Tree)", "O(log N)", "基於平衡二元搜尋樹，能維護 key 的排序順序並支援高效 subMap/range 操作。"));
        decisions.add(new ScenarioDecision(3, "急診叫號（優先權最高者優先處理）", "PriorityQueue (Min/Max Heap)", "O(log N) Insert/Poll", "Heap 能在極低開銷下維持最值，pop 時永遠取出最高優先級的項目。"));
        decisions.add(new ScenarioDecision(4, "瀏覽器歷史紀錄（後進先出 LIFO）", "ArrayDeque (as Stack)", "O(1) Push/Pop", "專用雙端佇列，無 Vector/Stack 的同步開銷，高效實現後進先出操作。"));
        decisions.add(new ScenarioDecision(5, "排隊系統（先進先出 FIFO）", "LinkedList / ArrayDeque (as Queue)", "O(1) Offer/Poll", "佇列完美滿足先進先出語意，保證公平的排隊處理順序。"));
        decisions.add(new ScenarioDecision(6, "快速判斷元素是否已存在並過濾重複", "HashSet", "O(1) Avg Contains", "雜湊集合保證元素唯一性，且能在 O(1) 時間快速判定元素是否重複。"));
        decisions.add(new ScenarioDecision(7, "地圖導航與最短順路站點搜尋", "Unweighted Graph (Adjacency List + BFS)", "O(V + E)", "鄰接表能高效儲存圖結構，搭配 BFS 可求得無權重圖的最短邊數路徑。"));
        decisions.add(new ScenarioDecision(8, "物流運輸成本計算（帶權重網路）", "Weighted Graph + Dijkstra's Algorithm", "O((V + E) log V)", "帶權重圖搭配 PriorityQueue 實現 Dijkstra 演算法，能精確算出最低運輸成本。"));
        decisions.add(new ScenarioDecision(9, "大容量數據且大部分節點無連線（稀疏圖）", "Adjacency List", "O(V + E) Space", "僅記錄實際存在的邊，空間開銷遠低於矩陣 O(V^2)，最適合稀疏圖。"));
        decisions.add(new ScenarioDecision(10, "頻繁檢查兩節點間是否存在直接連線", "Adjacency Matrix", "O(1) Edge Query", "使用二維布林陣列，查詢 matrix[u][v] 即可在 O(1) 時間判斷連線。"));
        decisions.add(new ScenarioDecision(11, "保持資料插入順序且允許尾端高效擴充", "ArrayList", "O(1) Amortized Add", "連續記憶體儲存，支援 O(1) 索引隨機存取與均攤 O(1) 尾端新增。"));
        decisions.add(new ScenarioDecision(12, "Top-K 最熱門商品統計（即時維護前 K 筆）", "Min Heap (Fixed Size K)", "O(N log K)", "維持大小為 K 的最小堆積，只需比較堆頂，大幅省去全量 O(N log N) 排序的開銷。"));

        return decisions;
    }

    public static void main(String[] args) {
        System.out.println("=== Data Structure Decision Report (12 Scenarios) ===\n");
        List<ScenarioDecision> report = generateDecisionReport();
        for (ScenarioDecision decision : report) {
            System.out.println(decision);
        }
    }
}