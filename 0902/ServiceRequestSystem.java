import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class ServiceRequestSystem {

    public static class Request implements Comparable<Request> {
        private String id;
        private int priority;
        private int createdOrder;

        public Request(String id, int priority, int createdOrder) {
            this.id = id;
            this.priority = priority;
            this.createdOrder = createdOrder;
        }

        public String getId() { return id; }
        public int getPriority() { return priority; }
        public int getCreatedOrder() { return createdOrder; }

        @Override
        public int compareTo(Request other) {
            if (this.priority != other.priority) {
                return Integer.compare(other.priority, this.priority); // Higher priority first
            }
            return Integer.compare(this.createdOrder, other.createdOrder); // FCFS
        }

        @Override
        public String toString() {
            return "Request[ID=" + id + ", Priority=" + priority + ", Order=" + createdOrder + "]";
        }
    }

    private Map<String, Request> requestMap;
    private PriorityQueue<Request> priorityQueue;
    private int orderCounter;

    public ServiceRequestSystem() {
        requestMap = new HashMap<>();
        priorityQueue = new PriorityQueue<>();
        orderCounter = 0;
    }

    public boolean addRequest(String id, int priority) {
        if (id == null || requestMap.containsKey(id)) {
            return false;
        }
        orderCounter++;
        Request req = new Request(id, priority, orderCounter);
        requestMap.put(id, req);
        priorityQueue.offer(req);
        return true;
    }

    public Request getRequestById(String id) {
        return requestMap.get(id);
    }

    public Request processNextRequest() {
        while (!priorityQueue.isEmpty()) {
            Request top = priorityQueue.poll();
            if (requestMap.containsKey(top.getId())) {
                requestMap.remove(top.getId());
                return top;
            }
        }
        throw new NoSuchElementException("No pending requests in the system.");
    }

    public boolean cancelRequest(String id) {
        if (id == null || !requestMap.containsKey(id)) {
            return false;
        }
        Request removed = requestMap.remove(id);
        priorityQueue.remove(removed); // Lazy sync cleanup or explicit removal to maintain strict consistency
        return true;
    }

    public int getPendingCount() {
        return requestMap.size();
    }

    public static void main(String[] args) {
        ServiceRequestSystem system = new ServiceRequestSystem();

        System.out.println("=== Standard Case ===");
        system.addRequest("REQ01", 3);
        system.addRequest("REQ02", 5);
        system.addRequest("REQ03", 5);
        system.addRequest("REQ04", 1);

        System.out.println("Query REQ02 by ID: " + system.getRequestById("REQ02"));
        System.out.println("Cancel REQ02: " + system.cancelRequest("REQ02"));
        System.out.println("Pending Count after cancellation: " + system.getPendingCount());

        System.out.println("Process Next: " + system.processNextRequest());
        System.out.println("Process Next: " + system.processNextRequest());

        System.out.println("\n=== Boundary Cases ===");
        System.out.println("Cancel non-existent: " + system.cancelRequest("INVALID"));
        System.out.println("Add duplicate ID: " + system.addRequest("REQ01", 2));

        system.processNextRequest(); // Process remaining REQ04

        try {
            system.processNextRequest();
        } catch (NoSuchElementException e) {
            System.out.println("Correctly handled empty queue error: " + e.getMessage());
        }
    }
}