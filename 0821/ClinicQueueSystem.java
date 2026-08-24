import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

class Patient {
    private String chartNumber;
    private String name;

    public Patient(String chartNumber, String name) {
        this.chartNumber = chartNumber;
        this.name = name;
    }

    public String getChartNumber() {
        return chartNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "病歷號: " + chartNumber + " | 姓名: " + name;
    }
}

public class ClinicQueueSystem {
    private final Deque<Patient> waitingQueue = new ArrayDeque<>();
    private final List<Patient> completedList = new ArrayList<>();

    public void register(Patient patient) {
        if (patient != null) {
            waitingQueue.offerLast(patient);
            System.out.println("掛號成功：" + patient);
        }
    }

    public boolean cancelRegistration(String chartNumber) {
        if (chartNumber == null || waitingQueue.isEmpty()) {
            System.out.println("取消失敗：無效病歷號或佇列為空");
            return false;
        }

        Iterator<Patient> iterator = waitingQueue.iterator();
        while (iterator.hasNext()) {
            Patient p = iterator.next();
            if (chartNumber.equalsIgnoreCase(p.getChartNumber())) {
                iterator.remove();
                System.out.println("取消掛號成功：" + p);
                return true;
            }
        }

        System.out.println("取消失敗：找不到病歷號 " + chartNumber + " 的掛號紀錄");
        return false;
    }

    public Patient callNext() {
        if (waitingQueue.isEmpty()) {
            System.out.println("叫號失敗：目前無人在等候");
            return null;
        }

        Patient patient = waitingQueue.pollFirst();
        completedList.add(patient);
        System.out.println("請 " + patient.getName() + " (病歷號: " + patient.getChartNumber() + ") 到診間就診");
        return patient;
    }

    public Patient peekNext() {
        if (waitingQueue.isEmpty()) {
            System.out.println("看診預覽：目前無人在等候");
            return null;
        }
        return waitingQueue.peekFirst();
    }

    public void printCompletedList() {
        System.out.println("=== 當日已完成看診清單 ===");
        if (completedList.isEmpty()) {
            System.out.println("(今日尚無已完成看診的病患)");
            return;
        }
        for (int i = 0; i < completedList.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + completedList.get(i));
        }
    }

    public static void main(String[] args) {
        ClinicQueueSystem clinic = new ClinicQueueSystem();

        System.out.println("=== 1. 空佇列操作測試 ===");
        clinic.peekNext();
        clinic.callNext();
        clinic.cancelRegistration("P001");

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 2. 患者掛號 (FIFO 佇列) ===");
        clinic.register(new Patient("P001", "張小明"));
        clinic.register(new Patient("P002", "李小華"));
        clinic.register(new Patient("P003", "王大同"));
        clinic.register(new Patient("P004", "陳靜"));

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 3. 查看下一位預計看診病患 ===");
        System.out.println("下一位候診者：" + clinic.peekNext());

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 4. 取消指定病歷號掛號測試 ===");
        clinic.cancelRegistration("P002"); // 取消中間的李小華
        clinic.cancelRegistration("P999"); // 嘗試取消不存在的病歷號

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 5. 陸續叫號看診 (維持 FIFO 順序) ===");
        clinic.callNext();
        clinic.callNext();

        System.out.println("\n確認下一位看診者：");
        System.out.println("下一位候診者：" + clinic.peekNext());

        clinic.callNext();
        clinic.callNext();

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 6. 輸出當日看診完成紀錄 ===");
        clinic.printCompletedList();
    }
}