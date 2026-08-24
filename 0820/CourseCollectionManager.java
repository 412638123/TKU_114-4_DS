import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

class StudentEnrollment {
    private String studentId;
    private String name;
    private int score;
    private Set<String> tags;

    public StudentEnrollment(String studentId, String name, int score, Set<String> rawTags) {
        this.studentId = studentId;
        this.name = name;
        this.score = score < 0 ? 0 : (score > 100 ? 100 : score);
        this.tags = new HashSet<>();
        if (rawTags != null) {
            for (String tag : rawTags) {
                if (tag != null && !tag.trim().isEmpty()) {
                    this.tags.add(tag.trim());
                }
            }
        }
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score < 0 ? 0 : (score > 100 ? 100 : score);
    }

    public Set<String> getTags() {
        return tags;
    }

    public String getGrade() {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEnrollment that = (StudentEnrollment) o;
        return Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    @Override
    public String toString() {
        return String.format("學號: %-5s | 姓名: %-4s | 分數: %3d | 等級: %s | 標籤: %s",
                studentId, name, score, getGrade(), tags);
    }
}

public class CourseCollectionManager {
    private final List<StudentEnrollment> enrollmentList = new ArrayList<>();
    private final Set<StudentEnrollment> enrollmentSet = new HashSet<>();
    private final Map<String, StudentEnrollment> enrollmentMap = new HashMap<>();

    public boolean addEnrollment(StudentEnrollment enrollment) {
        if (enrollment == null) return false;

        if (enrollmentSet.contains(enrollment)) {
            System.out.println("新增失敗：學號 " + enrollment.getStudentId() + " 已存在。");
            return false;
        }

        enrollmentList.add(enrollment);
        enrollmentSet.add(enrollment);
        enrollmentMap.put(enrollment.getStudentId(), enrollment);
        return true;
    }

    public boolean updateScore(String studentId, int score) {
        StudentEnrollment target = enrollmentMap.get(studentId);
        if (target == null) {
            return false;
        }
        target.setScore(score);
        return true;
    }

    public List<StudentEnrollment> findByTag(String tag) {
        List<StudentEnrollment> result = new ArrayList<>();
        if (tag == null || tag.trim().isEmpty()) {
            return result;
        }
        String cleanTag = tag.trim();

        for (StudentEnrollment e : enrollmentList) {
            if (e.getTags().contains(cleanTag)) {
                result.add(e);
            }
        }
        return result;
    }

    public Map<String, Integer> scoreDistribution() {
        Map<String, Integer> distribution = new HashMap<>();
        distribution.put("A", 0);
        distribution.put("B", 0);
        distribution.put("C", 0);
        distribution.put("D", 0);
        distribution.put("F", 0);

        for (StudentEnrollment e : enrollmentList) {
            String grade = e.getGrade();
            distribution.put(grade, distribution.get(grade) + 1);
        }
        return distribution;
    }

    public List<StudentEnrollment> top(int count) {
        if (count <= 0) {
            return new ArrayList<>();
        }

        List<StudentEnrollment> sortedList = new ArrayList<>(enrollmentList);
        sortedList.sort(Comparator.comparingInt(StudentEnrollment::getScore).reversed()
                .thenComparing(StudentEnrollment::getStudentId));

        if (count >= sortedList.size()) {
            return sortedList;
        }
        return new ArrayList<>(sortedList.subList(0, count));
    }

    public void removeBelow(int minimum) {
        Iterator<StudentEnrollment> iterator = enrollmentList.iterator();
        while (iterator.hasNext()) {
            StudentEnrollment e = iterator.next();
            if (e.getScore() < minimum) {
                iterator.remove();
                enrollmentSet.remove(e);
                enrollmentMap.remove(e.getStudentId());
            }
        }
    }

    public void printAll() {
        if (enrollmentList.isEmpty()) {
            System.out.println("(名單為空)");
            return;
        }
        for (StudentEnrollment e : enrollmentList) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        CourseCollectionManager manager = new CourseCollectionManager();

        System.out.println("=== 1. 初始化並建立報名資料 (包含重複學號、同分與空白 Tag) ===");
        
        Set<String> tags1 = new HashSet<>();
        tags1.add("Java"); tags1.add("  "); tags1.add(null);
        
        Set<String> tags2 = new HashSet<>();
        tags2.add("Java"); tags2.add("Python");

        Set<String> tags3 = new HashSet<>();
        tags3.add("C++"); tags3.add("");

        Set<String> tags4 = new HashSet<>();
        tags4.add("Python");

        Set<String> tags5 = new HashSet<>();
        tags5.add("Java");

        Set<String> tags6 = new HashSet<>();
        tags6.add("  ");

        manager.addEnrollment(new StudentEnrollment("S001", "張小明", 95, tags1));
        manager.addEnrollment(new StudentEnrollment("S002", "李小華", 85, tags2));
        manager.addEnrollment(new StudentEnrollment("S003", "王大同", 85, tags3));
        manager.addEnrollment(new StudentEnrollment("S004", "陳靜", 55, tags4));
        manager.addEnrollment(new StudentEnrollment("S005", "林酷妹", 72, tags5));
        manager.addEnrollment(new StudentEnrollment("S006", "趙小妹", 40, tags6));

        System.out.println("\n測試重複學號新增 (S001)：");
        manager.addEnrollment(new StudentEnrollment("S001", "重複明", 100, tags1));

        System.out.println("\n【目前所有學生名單】");
        manager.printAll();

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 2. 測試 updateScore(studentId, score) ===");
        System.out.println("將 S004 (陳靜) 分數由 55 修改為 68：" + manager.updateScore("S004", 68));
        System.out.println("將不存在的 S999 修改分數：" + manager.updateScore("S999", 100));

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 3. 測試 findByTag(tag) ===");
        System.out.println("搜尋標籤 \"Java\"：");
        for (StudentEnrollment e : manager.findByTag("Java")) {
            System.out.println(e);
        }

        System.out.println("\n搜尋空白標籤 \"  \" (應過濾無結果)：");
        System.out.println("搜尋結果筆數：" + manager.findByTag("  ").size());

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 4. 測試 scoreDistribution() ===");
        Map<String, Integer> dist = manager.scoreDistribution();
        System.out.println("成績等級分佈：" + dist);

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 5. 測試 top(count) ===");
        System.out.println("取得前 3 名 (同分時以學號排序)：");
        for (StudentEnrollment e : manager.top(3)) {
            System.out.println(e);
        }

        System.out.println("\n取得前 10 名 (count 大於總人數時回傳全部)：");
        System.out.println("回傳筆數：" + manager.top(10).size());

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 6. 測試 removeBelow(60) 剔除不及格項目 ===");
        System.out.println("執行剔除未達 60 分資料...");
        manager.removeBelow(60);

        System.out.println("\n清理後的 List 內容：");
        manager.printAll();

        System.out.println("\n驗證同步性 - 更新 S004 成績為 90 (原本 68 分已保留，若未同步則 Map/Set 會失效)：");
        manager.updateScore("S004", 90);
        manager.printAll();
    }
}