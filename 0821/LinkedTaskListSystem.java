class Task {
    private String id;
    private String title;

    public Task(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "任務編號: " + id + " | 任務名稱: " + title;
    }
}

class TaskNode {
    Task task;
    TaskNode next;

    public TaskNode(Task task) {
        this.task = task;
        this.next = null;
    }
}

class TaskLinkedList {
    private TaskNode head;
    private int size;

    public TaskLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public boolean addFirst(Task task) {
        if (task == null || containsId(task.getId())) {
            return false;
        }
        TaskNode newNode = new TaskNode(task);
        newNode.next = head;
        head = newNode;
        size++;
        return true;
    }

    public boolean addLast(Task task) {
        if (task == null || containsId(task.getId())) {
            return false;
        }
        TaskNode newNode = new TaskNode(task);
        if (head == null) {
            head = newNode;
        } else {
            TaskNode curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = newNode;
        }
        size++;
        return true;
    }

    public Task findById(String id) {
        if (id == null) return null;
        TaskNode curr = head;
        while (curr != null) {
            if (id.equals(curr.task.getId())) {
                return curr.task;
            }
            curr = curr.next;
        }
        return null;
    }

    public boolean removeById(String id) {
        if (id == null || head == null) {
            return false;
        }

        if (id.equals(head.task.getId())) {
            head = head.next;
            size--;
            return true;
        }

        TaskNode curr = head;
        while (curr.next != null) {
            if (id.equals(curr.next.task.getId())) {
                curr.next = curr.next.next;
                size--;
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public boolean insertAfter(String existingId, Task task) {
        if (existingId == null || task == null || containsId(task.getId())) {
            return false;
        }

        TaskNode curr = head;
        while (curr != null) {
            if (existingId.equals(curr.task.getId())) {
                TaskNode newNode = new TaskNode(task);
                newNode.next = curr.next;
                curr.next = newNode;
                size++;
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printAll() {
        if (head == null) {
            System.out.println("(名單為空)");
            return;
        }
        TaskNode curr = head;
        while (curr != null) {
            System.out.print("[" + curr.task + "]");
            if (curr.next != null) {
                System.out.print(" -> ");
            }
            curr = curr.next;
        }
        System.out.println();
    }

    private boolean containsId(String id) {
        return findById(id) != null;
    }
}

public class LinkedTaskListSystem {
    public static void main(String[] args) {
        TaskLinkedList list = new TaskLinkedList();

        System.out.println("=== 1. 空 List 測試 ===");
        System.out.println("當前大小: " + list.size());
        System.out.print("印出內容: ");
        list.printAll();
        System.out.println("尋找 T01: " + list.findById("T01"));
        System.out.println("刪除 T01: " + list.removeById("T01"));

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 2. 新增節點測試 (addFirst, addLast) ===");
        System.out.println("addLast T02: " + list.addLast(new Task("T02", "撰寫報告")));
        System.out.println("addFirst T01: " + list.addFirst(new Task("T01", "開會預備")));
        System.out.println("addLast T04: " + list.addLast(new Task("T04", "部署系統")));
        System.out.print("目前清單內容: ");
        list.printAll();
        System.out.println("目前大小: " + list.size());

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 3. 測試重複 ID 阻擋 ===");
        System.out.println("嘗試重複新增 T01 (addFirst): " + list.addFirst(new Task("T01", "重複開會")));
        System.out.println("嘗試重複新增 T02 (addLast): " + list.addLast(new Task("T02", "重複報告")));

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 4. 測試 insertAfter ===");
        System.out.println("在 T02 後插入 T03: " + list.insertAfter("T02", new Task("T03", "程式審查")));
        System.out.println("在不存在的 T99 後插入: " + list.insertAfter("T99", new Task("T05", "無效任務")));
        System.out.println("插入重複 ID (T01): " + list.insertAfter("T02", new Task("T01", "重複任務")));
        System.out.print("插入後清單內容: ");
        list.printAll();

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 5. 測試刪除 operation (Head, Middle, Tail, 找不到) ===");
        System.out.println("刪除不存在的 ID (T99): " + list.removeById("T99"));

        System.out.println("\n刪除 Middle 節點 (T02): " + list.removeById("T02"));
        System.out.print("刪除 Middle 後: ");
        list.printAll();

        System.out.println("\n刪除 Head 節點 (T01): " + list.removeById("T01"));
        System.out.print("刪除 Head 後: ");
        list.printAll();

        System.out.println("\n刪除 Tail 節點 (T04): " + list.removeById("T04"));
        System.out.print("刪除 Tail 後: ");
        list.printAll();

        System.out.println("\n剩餘最後一個節點 (T03): " + list.removeById("T03"));
        System.out.print("刪除至完畢後: ");
        list.printAll();
        System.out.println("最終大小: " + list.size());
    }
}