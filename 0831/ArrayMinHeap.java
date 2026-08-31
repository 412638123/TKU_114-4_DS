import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayMinHeap {
    private int[] heap;
    private int size;

    public ArrayMinHeap() {
        this.heap = new int[4];
        this.size = 0;
    }

    public ArrayMinHeap(int initialCapacity) {
        if (initialCapacity <= 0) {
            initialCapacity = 4;
        }
        this.heap = new int[initialCapacity];
        this.size = 0;
    }

    public void add(int val) {
        ensureCapacity();
        heap[size] = val;
        siftUp(size);
        size++;
    }

    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return heap[0];
    }

    public int removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        int minVal = heap[0];
        heap[0] = heap[size - 1];
        size--;
        if (size > 0) {
            siftDown(0);
        }
        return minVal;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void snapshot() {
        int[] currentElements = Arrays.copyOf(heap, size);
        System.out.println("Heap (size=" + size + ", capacity=" + heap.length + "): " + Arrays.toString(currentElements));
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap[index] < heap[parentIndex]) {
                int temp = heap[index];
                heap[index] = heap[parentIndex];
                heap[parentIndex] = temp;
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        while (index < size) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            if (leftChild < size && heap[leftChild] < heap[smallest]) {
                smallest = leftChild;
            }
            if (rightChild < size && heap[rightChild] < heap[smallest]) {
                smallest = rightChild;
            }

            if (smallest != index) {
                int temp = heap[index];
                heap[index] = heap[smallest];
                heap[smallest] = temp;
                index = smallest;
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        ArrayMinHeap heap = new ArrayMinHeap(4);
        int[] testData = {45, 12, 85, 32, 89, 39, 69, 44, 42, 1, 68, 47, 27, 2, 91, 58, 73, 19, 35, 6};

        System.out.println("--- Inserting 20 elements ---");
        for (int val : testData) {
            heap.add(val);
        }
        heap.snapshot();

        System.out.println("\nPeek min: " + heap.peek());

        System.out.println("\n--- Removing elements in sorted order ---");
        while (!heap.isEmpty()) {
            System.out.print(heap.removeMin() + " ");
        }
        System.out.println();
    }
}