import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class ServiceTicket {
    private String id;
    private String serviceType;
    private String status;

    public ServiceTicket(String id, String serviceType) {
        this.id = id;
        this.serviceType = serviceType;
        this.status = "WAITING";
    }

    public String getId() {
        return id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("票券編號: %-6s | 服務項目: %-10s | 狀態: %s", id, serviceType, status);
    }
}

public class ServiceCenterWorkflow {
    private final Map<String, ServiceTicket> ticketMap = new HashMap<>();
    private final Deque<ServiceTicket> waitingQueue = new ArrayDeque<>();
    private final Deque<ServiceTicket> completedStack = new ArrayDeque<>();
    private final Set<String> existingIds = new HashSet<>();

    public boolean createTicket(String id, String serviceType) {
        if (id == null || id.trim().isEmpty() || serviceType == null || serviceType.trim().isEmpty()) {
            System.out.println("取號失敗：資料不完整");
            return false;
        }
        String cleanId = id.trim();
        if (existingIds.contains(cleanId)) {
            System.out.println("取號失敗：票券編號 " + cleanId + " 已存在");
            return false;
        }

        ServiceTicket ticket = new ServiceTicket(cleanId, serviceType.trim());
        existingIds.add(cleanId);
        ticketMap.put(cleanId, ticket);
        waitingQueue.offerLast(ticket);
        System.out.println("取號成功：" + ticket);
        return true;
    }

    public ServiceTicket processNext() {
        if (waitingQueue.isEmpty()) {
            System.out.println("叫號失敗：目前無等待處理的票券");
            return null;
        }

        ServiceTicket ticket = waitingQueue.pollFirst();
        ticket.setStatus("COMPLETED");
        completedStack.push(ticket);
        System.out.println("開始服務並完成：" + ticket);
        return ticket;
    }

    public boolean cancelWaiting(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("取消失敗：無效的編號");
            return false;
        }
        String cleanId = id.trim();
        ServiceTicket ticket = ticketMap.get(cleanId);

        if (ticket == null) {
            System.out.println("取消失敗：找不到票券編號 " + cleanId);
            return false;
        }

        if (!"WAITING".equals(ticket.getStatus())) {
            System.out.println("取消失敗：票券 " + cleanId + " 狀態為 " + ticket.getStatus() + "，非等候中");
            return false;
        }

        Iterator<ServiceTicket> iterator = waitingQueue.iterator();
        while (iterator.hasNext()) {
            ServiceTicket t = iterator.next();
            if (cleanId.equals(t.getId())) {
                iterator.remove();
                ticket.setStatus("CANCELLED");
                System.out.println("成功取消等待中的票券：" + ticket);
                return true;
            }
        }
        return false;
    }

    public ServiceTicket undoLastCompletion() {
        if (completedStack.isEmpty()) {
            System.out.println("Undo 失敗：目前無已完成的票券紀錄");
            return null;
        }

        ServiceTicket ticket = completedStack.pop();
        ticket.setStatus("WAITING");
        waitingQueue.offerFirst(ticket);
        System.out.println("Undo 成功：已將票券放回等待佇列前端 => " + ticket);
        return ticket;
    }

    public ServiceTicket findById(String id) {
        if (id == null) return null;
        return ticketMap.get(id.trim());
    }

    public void printSummary() {
        int waiting = 0;
        int completed = 0;
        int cancelled = 0;

        for (ServiceTicket t : ticketMap.values()) {
            switch (t.getStatus()) {
                case "WAITING": waiting++; break;
                case "COMPLETED": completed++; break;
                case "CANCELLED": cancelled++; break;
            }
        }

        System.out.println("\n=========================================");
        System.out.println("【服務中心工作流程統計報告】");
        System.out.println("-----------------------------------------");
        System.out.println("總發行票券數 : " + existingIds.size());
        System.out.println("等待中 (Queue): " + waiting);
        System.out.println("已完成 (Stack): " + completed);
        System.out.println("已取消人數    : " + cancelled);
        System.out.println("=========================================");
    }

    public static void main(String[] args) {
        ServiceCenterWorkflow workflow = new ServiceCenterWorkflow();

        System.out.println("=== 1. 取號測試 (含重複 ID 阻擋) ===");
        workflow.createTicket("T001", "開戶諮詢");
        workflow.createTicket("T002", "信用卡申請");
        workflow.createTicket("T003", "外匯業務");
        workflow.createTicket("T001", "重複取號測試");

        workflow.printSummary();

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 2. 取消功能測試 (取消等待中、取消不存在、取消已處理) ===");
        workflow.cancelWaiting("T999");
        workflow.cancelWaiting("T002");

        workflow.printSummary();

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 3. 處理服務與空 Queue 測試 ===");
        workflow.processNext(); // T001
        workflow.processNext(); // T003 (T002 已取消)

        workflow.cancelWaiting("T001");

        System.out.println("\n空 Queue 時呼叫叫號：");
        workflow.processNext();

        workflow.printSummary();

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 4. 連續兩次 Undo 測試 (放回 Queue 前端) ===");
        workflow.undoLastCompletion(); // 退回 T003
        workflow.undoLastCompletion(); // 退回 T001

        System.out.println("\n嘗試第三次 Undo (已無完成紀錄)：");
        workflow.undoLastCompletion();

        workflow.printSummary();

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 5. 驗證 Undo 後的處理順序 (應為 T001 -> T003) ===");
        workflow.processNext(); // T001
        workflow.processNext(); // T003

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 6. findById 快速查詢測試 ===");
        System.out.println("查詢 T001: " + workflow.findById("T001"));
        System.out.println("查詢 T002: " + workflow.findById("T002"));
        System.out.println("查詢 T888: " + workflow.findById("T888"));

        workflow.printSummary();
    }
}