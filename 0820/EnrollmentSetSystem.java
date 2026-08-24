import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Enrollment {
    private String studentId;
    private String courseCode;

    public Enrollment(String studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(studentId, that.studentId) && 
               Objects.equals(courseCode, that.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }

    @Override
    public String toString() {
        return "學號: " + studentId + " | 課程代碼: " + courseCode;
    }
}

public class EnrollmentSetSystem {
    public static void main(String[] args) {
        Set<Enrollment> enrollmentSet = new HashSet<>();

        System.out.println("=== 1. 新增課程報名測試 ===");
        
        Enrollment e1 = new Enrollment("S001", "CS101");
        boolean add1 = enrollmentSet.add(e1);
        System.out.println("新增 S001 報名 CS101：" + add1);
        Enrollment e2 = new Enrollment("S001", "CS102");
        boolean add2 = enrollmentSet.add(e2);
        System.out.println("新增 S001 報名 CS102 (不同課程)：" + add2);

        Enrollment e3 = new Enrollment("S002", "CS101");
        boolean add3 = enrollmentSet.add(e3);
        System.out.println("新增 S002 報名 CS101：" + add3);

        Enrollment e4 = new Enrollment("S001", "CS101");
        boolean add4 = enrollmentSet.add(e4);
        System.out.println("新增 S001 重複報名 CS101：" + add4);

        System.out.println("\n目前系統總報名筆數：" + enrollmentSet.size());

        System.out.println("\n=== 2. 使用新建立但身分相同 (S001, CS101) 的物件測試 contains() 與 remove() ===");

        Enrollment queryTarget = new Enrollment("S001", "CS101");

        boolean hasEnrollment = enrollmentSet.contains(queryTarget);
        System.out.println("檢查是否已報名 (S001, CS101)：" + hasEnrollment);

        boolean removeResult = enrollmentSet.remove(queryTarget);
        System.out.println("取消報名 (S001, CS101)：" + removeResult);

        boolean hasEnrollmentAfterRemove = enrollmentSet.contains(queryTarget);
        System.out.println("再次檢查是否已報名 (S001, CS101)：" + hasEnrollmentAfterRemove);

        System.out.println("\n最終系統剩餘報名資料：");
        for (Enrollment e : enrollmentSet) {
            System.out.println(e);
        }
    }
}