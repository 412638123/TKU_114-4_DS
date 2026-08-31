import java.util.ArrayList;
import java.util.List;

public class BookIsbnHashTable {

    private static class Node {
        String isbn;
        String title;
        Node next;

        Node(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
            this.next = null;
        }
    }

    private Node[] buckets;
    private int capacity;
    private int size;

    public BookIsbnHashTable() {
        this(7);
    }

    public BookIsbnHashTable(int capacity) {
        this.capacity = capacity <= 0 ? 7 : capacity;
        this.buckets = new Node[this.capacity];
        this.size = 0;
    }

    private int getBucketIndex(String isbn) {
        if (isbn == null) return 0;
        return Math.floorMod(isbn.hashCode(), capacity);
    }

    public void put(String isbn, String title) {
        if (isbn == null) return;
        int index = getBucketIndex(isbn);
        Node current = buckets[index];

        while (current != null) {
            if (current.isbn.equals(isbn)) {
                current.title = title;
                return;
            }
            current = current.next;
        }

        Node newNode = new Node(isbn, title);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
    }

    public String get(String isbn) {
        if (isbn == null) return null;
        int index = getBucketIndex(isbn);
        Node current = buckets[index];

        while (current != null) {
            if (current.isbn.equals(isbn)) {
                return current.title;
            }
            current = current.next;
        }
        return null;
    }

    public boolean remove(String isbn) {
        if (isbn == null) return false;
        int index = getBucketIndex(isbn);
        Node current = buckets[index];
        Node prev = null;

        while (current != null) {
            if (current.isbn.equals(isbn)) {
                if (prev == null) {
                    buckets[index] = current.next;
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

    public double getLoadFactor() {
        return (double) size / capacity;
    }

    public void bucketReport() {
        System.out.println("=== Bucket Report ===");
        for (int i = 0; i < capacity; i++) {
            System.out.print("Bucket " + i + ": ");
            Node current = buckets[i];
            List<String> items = new ArrayList<>();
            while (current != null) {
                items.add("[" + current.isbn + ": " + current.title + "]");
                current = current.next;
            }
            System.out.println(String.join(" -> ", items));
        }
        System.out.println("Total Size: " + size);
        System.out.printf("Load Factor: %.2f\n", getLoadFactor());
    }

    public static void main(String[] args) {
        BookIsbnHashTable library = new BookIsbnHashTable(5);

        library.put("978-0134685991", "Effective Java");
        library.put("978-0132350884", "Clean Code");
        library.put("978-0201633610", "Design Patterns");
        library.put("978-0134685991", "Effective Java 3rd Edition");

        library.bucketReport();

        System.out.println("\nSearch 978-0132350884: " + library.get("978-0132350884"));
        System.out.println("Search 123-4567890123: " + library.get("123-4567890123"));

        System.out.println("\nRemove 978-0132350884: " + library.remove("978-0132350884"));
        library.bucketReport();
    }
}