import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionChoiceReport {

    public static void main(String[] args) {

        System.out.println("==========================================================");
        System.out.println("            集合選擇報告與實作展示");
        System.out.println("==========================================================");

        System.out.println("\n【需求一】保留搜尋紀錄且允許重複");
        System.out.println("選擇介面：List<String>");
        System.out.println("選擇實作：ArrayList<String>");
        System.out.println("選擇理由：需要維持資料的「原始輸入順序」，且允許重複搜尋相同關鍵字。");
        System.out.println("----------------------------------------------------------");
        List<String> searchHistory = new ArrayList<>();
        searchHistory.add("Java Generics");
        searchHistory.add("ROBLOX");
        searchHistory.add("Java Generics");
        searchHistory.add("Python Tutorial");
        System.out.println("操作結果：搜尋紀錄清單 => " + searchHistory);

        System.out.println("\n【需求二】保存不重複會員編號");
        System.out.println("選擇介面：Set<String>");
        System.out.println("選擇實作：HashSet<String>");
        System.out.println("選擇理由：會員編號具唯一性，HashSet 可在 O(1) 平均時間判斷編號是否重複。");
        System.out.println("----------------------------------------------------------");
        Set<String> memberIds = new HashSet<>();
        System.out.println("新增 M001: " + memberIds.add("M001"));
        System.out.println("新增 M002: " + memberIds.add("M002"));
        System.out.println("新增 M001 (重複): " + memberIds.add("M001"));
        System.out.println("操作結果：不重複會員編號集合 => " + memberIds);

        System.out.println("\n【需求三】以學號查詢成績");
        System.out.println("選擇介面：Map<String, Integer>");
        System.out.println("選擇實作：HashMap<String, Integer>");
        System.out.println("選擇理由：學號與成績為 Key-Value 對映關係，HashMap 提供高效快速的鍵值對檢索。");
        System.out.println("----------------------------------------------------------");
        Map<String, Integer> studentScores = new HashMap<>();
        studentScores.put("S001", 95);
        studentScores.put("S002", 88);
        studentScores.put("S003", 72);
        System.out.println("查詢 S001 成績: " + studentScores.get("S001"));
        System.out.println("查詢 S002 成績: " + studentScores.get("S002"));
        System.out.println("查詢 S009 (不存在學號): " + studentScores.get("S009"));

        System.out.println("\n【需求四】依到達順序處理列印工作");
        System.out.println("選擇介面：Deque<String>");
        System.out.println("選擇實作：ArrayDeque<String> (作為 FIFO Queue 使用)");
        System.out.println("選擇理由：列印工作屬於先到先服務 (FIFO) 佇列，ArrayDeque 效能極佳。");
        System.out.println("----------------------------------------------------------");
        Deque<String> printQueue = new ArrayDeque<>();
        printQueue.offerLast("Doc1_Report.pdf");
        printQueue.offerLast("Doc2_Photo.jpg");
        printQueue.offerLast("Doc3_Homework.java");
        System.out.println("目前排隊列印工作: " + printQueue);
        System.out.println("處理列印工作: " + printQueue.pollFirst());
        System.out.println("處理列印工作: " + printQueue.pollFirst());
        System.out.println("剩餘列印工作: " + printQueue);

        System.out.println("\n【需求五】復原最近操作");
        System.out.println("選擇介面：Deque<String>");
        System.out.println("選擇實作：ArrayDeque<String> (作為 LIFO Stack 使用)");
        System.out.println("選擇理由：復原 (Undo) 操作屬於後進先出 (LIFO) 堆疊結構。");
        System.out.println("----------------------------------------------------------");
        Deque<String> actionStack = new ArrayDeque<>();
        actionStack.push("輸入文字 'Hello'");
        actionStack.push("修改字型大小");
        actionStack.push("刪除段落");
        System.out.println("歷史操作堆疊: " + actionStack);
        System.out.println("執行復原 (Undo): " + actionStack.pop());
        System.out.println("執行復原 (Undo): " + actionStack.pop());
        System.out.println("剩餘操作堆疊: " + actionStack);

        System.out.println("\n==========================================================");
        System.out.println("                  實作與報告輸出完成");
        System.out.println("==========================================================");
    }
}