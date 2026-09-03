package final_exam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Q02_MinHeapInsert {

    private final List<Integer> heap = new ArrayList<>();

    public void add(int value) {
        heap.add(value);
        bubbleUp(heap.size() - 1);
    }

    public Integer peek() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public List<Integer> snapshot() {
        return new ArrayList<>(heap);
    }

    public boolean isValidMinHeap() {
        for (int i = 0; i < heap.size(); i++) {
            int leftChildIndex = 2 * i + 1;
            int rightChildIndex = 2 * i + 2;

            if (leftChildIndex < heap.size() && heap.get(i) > heap.get(leftChildIndex)) {
                return false;
            }

            if (rightChildIndex < heap.size() && heap.get(i) > heap.get(rightChildIndex)) {
                return false;
            }
        }
        return true;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index) < heap.get(parentIndex)) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public static void main(String[] args) {
        Q02_MinHeapInsert heap = new Q02_MinHeapInsert();
        heap.add(10);
        heap.add(4);
        heap.add(15);
        heap.add(2);

        System.out.println("Q02 Peek 最小值: " + heap.peek());
        System.out.println("Q02 Heap 快照: " + heap.snapshot());
        System.out.println("Q02 是否為有效 MinHeap: " + heap.isValidMinHeap());
    }
}