import java.util.ArrayList;
import java.util.List;

public class IntegratedStructureAudit {

    public enum AuditVerdict {
        OPTIMAL,
        SUBOPTIMAL,
        INCORRECT
    }

    public static class AuditScenario {
        private int scenarioId;
        private String description;
        private String chosenStructure;
        private AuditVerdict verdict;
        private String recommendedStructure;
        private String explanation;

        public AuditScenario(int scenarioId, String description, String chosenStructure, AuditVerdict verdict, String recommendedStructure, String explanation) {
            this.scenarioId = scenarioId;
            this.description = description;
            this.chosenStructure = chosenStructure;
            this.verdict = verdict;
            this.recommendedStructure = recommendedStructure;
            this.explanation = explanation;
        }

        @Override
        public String toString() {
            return String.format("[%d] %s\n    Chosen: %s | Verdict: %s | Recommended: %s\n    Audit: %explanation%\n"
                    .replace("%explanation%", explanation), scenarioId, description, chosenStructure, verdict, recommendedStructure);
        }
    }

    public static List<AuditScenario> runAudit() {
        List<AuditScenario> auditResults = new ArrayList<>();

        auditResults.add(new AuditScenario(
            1,
            "高頻率使用 Key 進行 O(1) 會員資料尋找",
            "ArrayList",
            AuditVerdict.INCORRECT,
            "Hash Table (HashMap)",
            "ArrayList 的 Contains/GetByValue 為 O(N) 線性搜尋，不符合 O(1) 需求；應改用 HashMap。"
        ));

        auditResults.add(new AuditScenario(
            2,
            "即時維護急診病患優先級並每次取出最高優先者",
            "PriorityQueue (Heap)",
            AuditVerdict.OPTIMAL,
            "PriorityQueue (Heap)",
            "Heap 可在 O(log N) 時間完成插入與取頂點，完美符合優先權佇列需求。"
        ));

        auditResults.add(new AuditScenario(
            3,
            "捷運站點網路地圖與最短轉乘路徑規劃",
            "Graph (Adjacency List + BFS)",
            AuditVerdict.OPTIMAL,
            "Graph (Adjacency List + BFS)",
            "地圖結構為典型圖形，使用鄰接表搭配 BFS 能正確且高效地還原最少站數路徑。"
        ));

        auditResults.add(new AuditScenario(
            4,
            "資料庫索引需動態維持排序並支援範圍搜尋",
            "Unsorted List",
            AuditVerdict.INCORRECT,
            "BST (Red-Black Tree / TreeMap)",
            "未排序清單無法進行高效範圍搜尋；應使用平衡二元搜尋樹以支援 O(log N) 範圍查詢。"
        ));

        auditResults.add(new AuditScenario(
            5,
            "處理作業系統的 CPU 任務排程（FIFO 佇列）",
            "Queue (ArrayDeque)",
            AuditVerdict.OPTIMAL,
            "Queue (ArrayDeque)",
            "FIFO 佇列結構能保證任務依到達順序公平執行，空間與時間開銷皆為 O(1)。"
        ));

        auditResults.add(new AuditScenario(
            6,
            "社群網路好友關係及共同好友過濾",
            "Set (HashSet / Graph)",
            AuditVerdict.OPTIMAL,
            "Set (HashSet / Graph)",
            "HashSet 能在 O(1) 時間判定好友是否存在，並可使用 RetainAll 快速計算交集。"
        ));

        return auditResults;
    }

    public static void main(String[] args) {
        System.out.println("=== Integrated Data Structure Audit Report ===\n");
        List<AuditScenario> results = runAudit();

        int optimalCount = 0;
        for (AuditScenario scenario : results) {
            System.out.println(scenario);
            if (scenario.verdict == AuditVerdict.OPTIMAL) {
                optimalCount++;
            }
        }

        System.out.println("==============================================");
        System.out.println("Audit Summary: " + optimalCount + "/" + results.size() + " scenarios passed optimal validation.");
    }
}