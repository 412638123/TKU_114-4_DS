import java.util.Arrays;

class CircularQueue<T> {
    private final Object[] data;
    private int front;
    private int rear;
    private int size;
    private final int capacity;

    @SuppressWarnings("unchecked")
    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.data = new Object[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public boolean enqueue(T item) {
        if (isFull() || item == null) {
            return false;
        }
        data[rear] = item;
        rear = (rear + 1) % capacity;
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T item = (T) data[front];
        data[front] = null;
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int size() {
        return size;
    }

    public int getFront() {
        return front;
    }

    public int getRear() {
        return rear;
    }

    public void printState(String action) {
        System.out.printf("%-18s | Array: %-22s | Front: %d | Rear: %d | Size: %d%n",
                action, Arrays.toString(data), front, rear, size);
    }
}

public class CircularQueuePractice {
    public static void main(String[] args) {
        CircularQueue<String> queue = new CircularQueue<>(4);

        System.out.println("=== 環形隊列 (Circular Queue) 狀態追蹤 (容量 4) ===");
        queue.printState("初始狀態");

        System.out.println("\n----------------------------------------------------------------------------------");

        String[] operations = {
            "enqueue A", "enqueue B", "enqueue C",
            "dequeue", "dequeue",
            "enqueue D", "enqueue E", "enqueue F",
            "dequeue", "enqueue G"
        };

        for (String op : operations) {
            if (op.startsWith("enqueue")) {
                String val = op.split(" ")[1];
                boolean success = queue.enqueue(val);
                queue.printState(op + (success ? "" : " (失敗:已滿)"));
            } else if (op.equals("dequeue")) {
                String val = queue.dequeue();
                queue.printState("dequeue -> " + (val == null ? "null" : val));
            }
        }

        System.out.println("----------------------------------------------------------------------------------\n");

        System.out.println("=== 依 FIFO 順序取出隊列中剩餘的所有元素 ===");
        System.out.print("取出順序: ");
        while (!queue.isEmpty()) {
            System.out.print(queue.dequeue() + (queue.isEmpty() ? "" : " -> "));
        }
        System.out.println();
    }
}