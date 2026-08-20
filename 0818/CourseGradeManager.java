class CourseGrade {
    private String studentId;
    private String name;
    private double dailyScore;
    private double midtermScore;
    private double finalScore;
    private double attendanceScore;

    public CourseGrade(String studentId, String name, double dailyScore, double midtermScore, double finalScore, double attendanceScore) {
        this.studentId = studentId;
        this.name = name;
        this.dailyScore = clamp(dailyScore);
        this.midtermScore = clamp(midtermScore);
        this.finalScore = clamp(finalScore);
        this.attendanceScore = clamp(attendanceScore);
    }

    private double clamp(double score) {
        if (score < 0) return 0;
        if (score > 100) return 100;
        return score;
    }

    public double calculateFinalScore() {
        return (dailyScore * 0.5) + (midtermScore * 0.2) + (finalScore * 0.2) + (attendanceScore * 0.1);
    }

    public String getLevel() {
        double total = calculateFinalScore();
        if (total >= 90) return "A";
        if (total >= 80) return "B";
        if (total >= 70) return "C";
        if (total >= 60) return "D";
        return "F";
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "學號：" + studentId + " | 姓名：" + name + 
               " | 平時：" + dailyScore + " | 期中：" + midtermScore + 
               " | 期末：" + finalScore + " | 出席：" + attendanceScore + 
               " | 總分：" + calculateFinalScore() + " | 等級：" + getLevel();
    }
}

public class CourseGradeManager {
    public static void main(String[] args) {
        CourseGrade[] students = {
            new CourseGrade("S001", "張小明", 85, 90, 88, 100),
            new CourseGrade("S002", "李小華", 60, 55, 50, 70),
            new CourseGrade("S003", "王大同", 95, 92, 98, 90),
            new CourseGrade("S004", "趙小玲", 40, 50, 45, 60),
            new CourseGrade("S005", "陳阿強", 75, 80, 70, 85)
        };

        System.out.println("【所有學生成績清單】");
        for (CourseGrade student : students) {
            System.out.println(student);
        }

        double totalSum = 0;
        for (CourseGrade student : students) {
            totalSum += student.calculateFinalScore();
        }
        double average = totalSum / students.length;
        System.out.println("\n【班級總平均】\n" + average + " 分");

        CourseGrade topStudent = students[0];
        for (int i = 1; i < students.length; i++) {
            if (students[i].calculateFinalScore() > topStudent.calculateFinalScore()) {
                topStudent = students[i];
            }
        }
        System.out.println("\n【最高分學生】\n" + topStudent.getName() + " (學號: " + topStudent.getStudentId() + ") - 總分: " + topStudent.calculateFinalScore());

        System.out.println("\n【不及格名單 (總分 < 60)】");
        boolean hasFailed = false;
        for (CourseGrade student : students) {
            if (student.calculateFinalScore() < 60) {
                System.out.println(student.getName() + " (學號: " + student.getStudentId() + ") - 總分: " + student.calculateFinalScore());
                hasFailed = true;
            }
        }
        if (!hasFailed) {
            System.out.println("無不及格學生");
        }
    }
}