import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class IntegerMinHeap {
    private List<Integer> heap;

    public IntegerMinHeap() {
        heap = new ArrayList<>();
    }

    public void add(int val) {
        heap.add(val);
        siftUp(heap.size() - 1);
    }

    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return heap.get(0);
    }

    public int removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int minVal = heap.get(0);
        int lastVal = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, lastVal);
            siftDown(0);
        }
        return minVal;
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index) < heap.get(parentIndex)) {
                int temp = heap.get(index);
                heap.set(index, heap.get(parentIndex));
                heap.set(parentIndex, temp);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        int size = heap.size();
        while (index < size) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            if (leftChild < size && heap.get(leftChild) < heap.get(smallest)) {
                smallest = leftChild;
            }
            if (rightChild < size && heap.get(rightChild) < heap.get(smallest)) {
                smallest = rightChild;
            }

            if (smallest != index) {
                int temp = heap.get(index);
                heap.set(index, heap.get(smallest));
                heap.set(smallest, temp);
                index = smallest;
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        IntegerMinHeap minHeap = new IntegerMinHeap();
        int[] testData = {25, 40, 10, 50, 30, 50};

        for (int val : testData) {
            minHeap.add(val);
        }

        System.out.print("Removed in non-decreasing order: ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.removeMin() + " ");
        }
        System.out.println();

        try {
            minHeap.peek();
        } catch (NoSuchElementException e) {
            System.out.println("peek() correctly threw NoSuchElementException on empty heap.");
        }

        try {
            minHeap.removeMin();
        } catch (NoSuchElementException e) {
            System.out.println("removeMin() correctly threw NoSuchElementException on empty heap.");
        }
    }
}