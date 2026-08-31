import java.util.ArrayList;
import java.util.List;

public class MaxHeapInsertTrace {
    private List<Integer> heap;

    public MaxHeapInsertTrace() {
        heap = new ArrayList<>();
    }

    public void add(int val) {
        heap.add(val);
        siftUp(heap.size() - 1);
    }

    public int peekMax() {
        if (heap.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return heap.get(0);
    }

    public void snapshot() {
        System.out.println(heap);
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index) > heap.get(parentIndex)) {
                int temp = heap.get(index);
                heap.set(index, heap.get(parentIndex));
                heap.set(parentIndex, temp);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        MaxHeapInsertTrace maxHeap = new MaxHeapInsertTrace();
        int[] input = {25, 40, 10, 50, 30, 50};

        for (int val : input) {
            maxHeap.add(val);
            maxHeap.snapshot();
        }

        System.out.println("Root: " + maxHeap.peekMax());
    }
}