abstract class Employee {
    private String id;
    private String name;

    public Employee(String id, String name) {
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

    @Override
    public String toString() {
        return "ID: " + id + " | 姓名: " + name;
    }
}

class SalariedEmployee extends Employee {
    private double monthlySalary;
    private double bonus;

    public SalariedEmployee(String id, String name, double monthlySalary, double bonus) {
        super(id, name);
        this.monthlySalary = monthlySalary < 0 ? 0 : monthlySalary;
        this.bonus = bonus < 0 ? 0 : bonus;
    }

    @Override
    public double calculatePay() {
        return monthlySalary + bonus;
    }

    @Override
    public String toString() {
        return super.toString() + " | 類型: 月薪員工 | 應發薪資: " + calculatePay();
    }
}

class HourlyEmployee extends Employee {
    private double hourlyRate;
    private double hoursWorked;

    public HourlyEmployee(String id, String name, double hourlyRate, double hoursWorked) {
        super(id, name);
        this.hourlyRate = hourlyRate < 0 ? 0 : hourlyRate;
        this.hoursWorked = hoursWorked < 0 ? 0 : hoursWorked;
    }

    @Override
    public double calculatePay() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public String toString() {
        return super.toString() + " | 類型: 時薪員工 | 應發薪資: " + calculatePay();
    }
}

class CommissionEmployee extends Employee {
    private double baseSalary;
    private double salesAmount;
    private double commissionRate;

    public CommissionEmployee(String id, String name, double baseSalary, double salesAmount, double commissionRate) {
        super(id, name);
        this.baseSalary = baseSalary < 0 ? 0 : baseSalary;
        this.salesAmount = salesAmount < 0 ? 0 : salesAmount;
        this.commissionRate = commissionRate < 0 ? 0 : commissionRate;
    }

    @Override
    public double calculatePay() {
        return baseSalary + (salesAmount * commissionRate);
    }

    @Override
    public String toString() {
        return super.toString() + " | 類型: 業務員工 | 應發薪資: " + calculatePay();
    }
}

public class PayrollPolymorphismSystem {
    public static void main(String[] args) {
        Employee[] employees = {
            new SalariedEmployee("E001", "張小明", 45000, 5000),
            new HourlyEmployee("E002", "李小華", 200, 160),
            new CommissionEmployee("E003", "王大同", 28000, 300000, 0.08)
        };

        System.out.println("【所有員工薪資明細】");
        double totalPayroll = 0;
        Employee highestPaid = employees[0];

        for (Employee emp : employees) {
            System.out.println(emp);
            double pay = emp.calculatePay();
            totalPayroll += pay;

            if (pay > highestPaid.calculatePay()) {
                highestPaid = emp;
            }
        }

        System.out.println("\n【統計結果】");
        System.out.println("薪資總額：" + totalPayroll + " 元");
        System.out.println("最高薪資員工：" + highestPaid.getName() + " (ID: " + highestPaid.getId() + ") - " + highestPaid.calculatePay() + " 元");
    }
}