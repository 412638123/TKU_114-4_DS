import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EnrollmentCleanup {
    public static void main(String[] args) {
        List<String> enrollmentList = new ArrayList<>();
        enrollmentList.add("張小明");
        enrollmentList.add("  ");
        enrollmentList.add("李小華");
        enrollmentList.add(null);
        enrollmentList.add("張小明");
        enrollmentList.add("王大同");
        enrollmentList.add("");
        enrollmentList.add("李小華");
        enrollmentList.add("陳靜");

        System.out.println("=== 1. 清理前的原始名單 ===");
        System.out.println(enrollmentList);

        // 使用 Set 找出重複出現的姓名
        Set<String> seenNames = new HashSet<>();
        Set<String> duplicateNames = new HashSet<>();

        for (String name : enrollmentList) {
            if (name != null && !name.trim().isEmpty()) {
                String cleanName = name.trim();
                if (!seenNames.add(cleanName)) {
                    duplicateNames.add(cleanName);
                }
            }
        }

        // 使用 Iterator 安全地移除 null 與空白（包含純空格）資料
        Iterator<String> iterator = enrollmentList.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            if (name == null || name.trim().isEmpty()) {
                iterator.remove();
            }
        }

        System.out.println("\n=== 2. 清理後的無效資料排除名單 ===");
        System.out.println(enrollmentList);

        System.out.println("\n=== 3. 重複姓名報告 (Set) ===");
        System.out.println("發現重複的姓名：" + duplicateNames);
    }
}