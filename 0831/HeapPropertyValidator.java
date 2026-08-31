import java.util.List;

public class HeapPropertyValidator {

    public static boolean isMinHeap(List<Integer> list) {
        if (list == null) {
            return false;
        }
        int size = list.size();
        if (size <= 1) {
            return true;
        }

        for (int i = 0; i < size; i++) {
            if (list.get(i) == null) {
                return false;
            }
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < size) {
                if (list.get(left) == null || list.get(i) > list.get(left)) {
                    return false;
                }
            }
            if (right < size) {
                if (list.get(right) == null || list.get(i) > list.get(right)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isMaxHeap(List<Integer> list) {
        if (list == null) {
            return false;
        }
        int size = list.size();
        if (size <= 1) {
            return true;
        }

        for (int i = 0; i < size; i++) {
            if (list.get(i) == null) {
                return false;
            }
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < size) {
                if (list.get(left) == null || list.get(i) < list.get(left)) {
                    return false;
                }
            }
            if (right < size) {
                if (list.get(right) == null || list.get(i) < list.get(right)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        List<Integer> minHeap = List.of(10, 25, 30, 40, 50, 50);
        List<Integer> maxHeap = List.of(50, 40, 50, 25, 30, 10);
        List<Integer> invalid = List.of(50, 10, 40);

        System.out.println("minHeap isMinHeap: " + isMinHeap(minHeap));
        System.out.println("maxHeap isMaxHeap: " + isMaxHeap(maxHeap));
        System.out.println("invalid isMinHeap: " + isMinHeap(invalid));
        System.out.println("invalid isMaxHeap: " + isMaxHeap(invalid));
    }
}