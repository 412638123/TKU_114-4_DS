abstract class EmployeeBase {
    private String id;
    private String name;

    public EmployeeBase(String id, String name) {
        System.out.println("EmployeeBase Constructor 執行");
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract double calculatePay();
}

class FullTimeEmployee extends EmployeeBase {
    private double monthlySalary;

    public FullTimeEmployee(String id, String name, double monthlySalary) {
        super(id, name);
        System.out.println("FullTimeEmployee Constructor 執行");
        this.monthlySalary = monthlySalary < 0 ? 0 : monthlySalary;
    }

    @Override
    public double calculatePay() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends EmployeeBase {
    private double hourlyRate;
    private double hoursWorked;

    public PartTimeEmployee(String id, String name, double hourlyRate, double hoursWorked) {
        super(id, name);
        System.out.println("PartTimeEmployee Constructor 執行");
        this.hourlyRate = hourlyRate < 0 ? 0 : hourlyRate;
        this.hoursWorked = hoursWorked < 0 ? 0 : hoursWorked;
    }

    @Override
    public double calculatePay() {
        return hourlyRate * hoursWorked;
    }
}

public class EmployeeConstructorChain {
    public static void main(String[] args) {
        System.out.println("=== 1. 建立正職社員物件 ===");
        FullTimeEmployee ft = new FullTimeEmployee("E001", "張小明", 45000);
        System.out.println("薪資計算：" + ft.calculatePay());

        System.out.println("\n=== 2. 建立兼職社員物件（含負數邊界值測試）===");
        PartTimeEmployee pt = new PartTimeEmployee("E002", "李小華", -160, -80);
        System.out.println("薪資計算：" + pt.calculatePay());

        System.out.println("\n=== 3. Constructor 實際執行順序說明 ===");
        System.out.println("當建立子類別物件（如 FullTimeEmployee）時：");
        System.out.println("步驟 1：先呼叫 super(...)，執行父類別 EmployeeBase 的 Constructor。");
        System.out.println("步驟 2：再執行子類別 FullTimeEmployee 本身的 Constructor。");
        System.out.println("因此主控台印出的順序為 EmployeeBase -> FullTimeEmployee。");
    }
}