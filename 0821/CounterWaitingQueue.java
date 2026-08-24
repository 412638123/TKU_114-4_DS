import java.util.ArrayDeque;
import java.util.Deque;

class Customer {
    private String id;
    private String name;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "顧客編號: " + id + " | 姓名: " + name;
    }
}

public class CounterWaitingQueue {
    private final Deque<Customer> queue = new ArrayDeque<>();

    public void addCustomer(Customer customer) {
        if (customer != null) {
            queue.offerLast(customer);
            System.out.println("成功排隊：" + customer);
        }
    }

    public Customer peekNext() {
        if (queue.isEmpty()) {
            System.out.println("查看下一位：當前無人在等候");
            return null;
        }
        return queue.peekFirst();
    }

    public Customer serveNext() {
        if (queue.isEmpty()) {
            System.out.println("服務下一位：當前無人在等候，無法服務");
            return null;
        }
        Customer customer = queue.pollFirst();
        System.out.println("開始服務：" + customer);
        return customer;
    }

    public int getWaitingCount() {
        return queue.size();
    }

    public static void main(String[] args) {
        CounterWaitingQueue counter = new CounterWaitingQueue();

        System.out.println("=== 1. 空隊列操作測試 ===");
        System.out.println("當前等候人數：" + counter.getWaitingCount());
        counter.peekNext();
        counter.serveNext();

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 2. 顧客排隊測試 ===");
        counter.addCustomer(new Customer("C001", "張小明"));
        counter.addCustomer(new Customer("C002", "李小華"));
        counter.addCustomer(new Customer("C003", "王大同"));
        System.out.println("當前等候人數：" + counter.getWaitingCount());

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 3. 查看與服務測試 (FIFO) ===");
        System.out.println("下一位準備接受服務者：" + counter.peekNext());
        System.out.println("目前總等候人數：" + counter.getWaitingCount());

        System.out.println();
        counter.serveNext();
        System.out.println("服務完成後，剩餘等候人數：" + counter.getWaitingCount());

        System.out.println();
        counter.serveNext();
        System.out.println("服務完成後，剩餘等候人數：" + counter.getWaitingCount());

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 4. 服務最後一位顧客與空隊列驗證 ===");
        counter.serveNext();
        System.out.println("當前等候人數：" + counter.getWaitingCount());

        System.out.println();
        counter.serveNext();
    }
}