import java.util.ArrayList;
import java.util.List;

public class IntegerStringHashTable {

    private static class Node {
        int key;
        String value;
        Node next;

        Node(int key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Node[] table;
    private int capacity;
    private int size;

    public IntegerStringHashTable() {
        this(10);
    }

    public IntegerStringHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new Node[capacity];
        this.size = 0;
    }

    private int getBucketIndex(int key) {
        return Math.floorMod(key, capacity);
    }

    public void put(int key, String value) {
        int index = getBucketIndex(key);
        Node current = table[index];

        while (current != null) {
            if (current.key == key) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        Node newNode = new Node(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
    }

    public String get(int key) {
        int index = getBucketIndex(key);
        Node current = table[index];

        while (current != null) {
            if (current.key == key) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public boolean containsKey(int key) {
        return get(key) != null;
    }

    public boolean remove(int key) {
        int index = getBucketIndex(key);
        Node current = table[index];
        Node prev = null;

        while (current != null) {
            if (current.key == key) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void bucketReport() {
        System.out.println("=== Bucket Report ===");
        for (int i = 0; i < capacity; i++) {
            System.out.print("Bucket " + i + ": ");
            Node current = table[i];
            List<String> items = new ArrayList<>();
            while (current != null) {
                items.add("[" + current.key + ": " + current.value + "]");
                current = current.next;
            }
            System.out.println(String.join(" -> ", items));
        }
        System.out.println("Total Size: " + size);
    }

    public static void main(String[] args) {
        IntegerStringHashTable ht = new IntegerStringHashTable(5);

        ht.put(1, "Alice");
        ht.put(6, "Bob");
        ht.put(11, "Charlie");
        ht.put(2, "David");

        ht.bucketReport();

        System.out.println("\nUpdate Key 6...");
        ht.put(6, "Bobby");
        System.out.println("Get 6: " + ht.get(6));
        System.out.println("Size after update: " + ht.size());

        System.out.println("\nRemove Key 1...");
        ht.remove(1);
        ht.bucketReport();
    }
}