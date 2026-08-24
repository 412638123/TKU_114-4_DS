import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseTagReport {
    public static void main(String[] args) {
        String[] rawTags = {"Java", "Python", "Java", "Spring", "Python", "Docker", "Java", "SQL"};

        List<String> tagList = new ArrayList<>();
        Set<String> tagSet = new HashSet<>();
        Map<String, Integer> tagCountMap = new HashMap<>();

        for (String tag : rawTags) {
            tagList.add(tag);
            tagSet.add(tag);
            tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
        }

        System.out.println("=== 1. List (保存原始輸入順序) ===");
        System.out.println("標籤清單：" + tagList);

        System.out.println("\n=== 2. Set (保存不重複標籤集合) ===");
        System.out.println("去重後標籤：" + tagSet);

        System.out.println("\n=== 3. Map (統計各標籤出現次數) ===");
        for (Map.Entry<String, Integer> entry : tagCountMap.entrySet()) {
            System.out.println("標籤: " + entry.getKey() + " | 次數: " + entry.getValue());
        }

        System.out.println("\n========================================");
        System.out.println("【集合容器用途說明】");
        System.out.println("========================================");
        System.out.println("1. List：具備順序性，允許元素重複。適合用於需要完整保留資料輸入順序、或允許重複紀錄的場景（如歷程紀錄、瀏覽順序）。");
        System.out.println("2. Set：具備唯一性，自動過濾重複項目。適合用於快速判斷資料是否存在、或是獲取去重後的獨立列表（如標籤篩選器、分類清單）。");
        System.out.println("3. Map：採用 Key-Value 鍵值對形式儲存。適合用於鍵值對對映與計數統計（如統計各標籤數量、快速根據 Key 尋找對應 Value）。");
    }
}