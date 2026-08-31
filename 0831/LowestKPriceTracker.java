import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class LowestKPriceTracker {
    public static List<Integer> getLowestKPrices(List<Integer> prices, int k) {
        if (k <= 0 || prices == null) {
            return new ArrayList<>();
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (Integer price : prices) {
            if (price == null || price < 0) {
                continue;
            }

            if (maxHeap.size() < k) {
                maxHeap.offer(price);
            } else if (!maxHeap.isEmpty() && price < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.offer(price);
            }
        }

        List<Integer> result = new ArrayList<>(maxHeap);
        Collections.sort(result);
        return result;
    }

    public static void main(String[] args) {
        List<Integer> prices = List.of(50, 10, -5, 30, 20, 5, 100);
        int k = 3;

        List<Integer> lowestK = getLowestKPrices(prices, k);
        System.out.println(lowestK);
    }
}