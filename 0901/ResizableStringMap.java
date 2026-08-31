import java.util.ArrayList;
import java.util.List;

public class ResizableStringMap {

    private static class Entry {
        String key;
        String value;
        Entry next;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Entry[] buckets;
    private int capacity;
    private int size;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    public ResizableStringMap() {
        this(7);
    }

    public ResizableStringMap(int initialCapacity) {
        this.capacity = initialCapacity;
        this.buckets = new Entry[capacity];
        this.size = 0;
    }

    private int getBucketIndex(String key, int currentCapacity) {
        if (key == null) return 0;
        return Math.floorMod(key.hashCode(), currentCapacity);
    }

    public void put(String key, String value) {
        int index = getBucketIndex(key, capacity);
        Entry current = buckets[index];

        while (current != null) {
            if ((current.key == null && key == null) || (current.key != null && current.key.equals(key))) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        Entry newEntry = new Entry(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;

        if ((double) size / capacity > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    public String get(String key) {
        int index = getBucketIndex(key, capacity);
        Entry current = buckets[index];

        while (current != null) {
            if ((current.key == null && key == null) || (current.key != null && current.key.equals(key))) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }

    public boolean remove(String key) {
        int index = getBucketIndex(key, capacity);
        Entry current = buckets[index];
        Entry prev = null;

        while (current != null) {
            if ((current.key == null && key == null) || (current.key != null && current.key.equals(key))) {
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

    public int getCapacity() {
        return capacity;
    }

    private void resize() {
        int newCapacity = capacity * 2 + 1;
        Entry[] newBuckets = new Entry[newCapacity];

        for (int i = 0; i < capacity; i++) {
            Entry current = buckets[i];
            while (current != null) {
                Entry next = current.next;
                int newIndex = getBucketIndex(current.key, newCapacity);

                current.next = newBuckets[newIndex];
                newBuckets[newIndex] = current;

                current = next;
            }
        }

        this.capacity = newCapacity;
        this.buckets = newBuckets;
    }

    public void printStatus() {
        System.out.println("Size: " + size + ", Capacity: " + capacity + ", Load Factor: " + String.format("%.2f", (double) size / capacity));
    }

    public static void main(String[] args) {
        ResizableStringMap map = new ResizableStringMap(3);

        System.out.println("--- Adding elements ---");
        map.put("CS101", "Java Programming");
        map.printStatus();
        map.put("CS102", "Data Structures");
        map.printStatus();
        map.put("CS103", "Algorithms");
        map.printStatus();

        map.put("CS104", "Database Systems");
        map.printStatus();

        System.out.println("\nGet CS102: " + map.get("CS102"));
        System.out.println("Contains CS105: " + map.containsKey("CS105"));
    }
}