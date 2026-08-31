import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopSellingProducts {

    public static class Product implements Comparable<Product> {
        private String id;
        private int sales;

        public Product(String id, int sales) {
            this.id = id;
            this.sales = sales;
        }

        public String getId() {
            return id;
        }

        public int getSales() {
            return sales;
        }

        @Override
        public int compareTo(Product other) {
            if (this.sales != other.sales) {
                return Integer.compare(this.sales, other.sales);
            }
            return other.id.compareTo(this.id);
        }

        @Override
        public String toString() {
            return id + "(" + sales + ")";
        }
    }

    public static List<Product> getTopKProducts(List<Product> products, int k) {
        if (k <= 0 || products == null || products.isEmpty()) {
            return new ArrayList<>();
        }

        Map<String, Integer> salesMap = new HashMap<>();
        for (Product p : products) {
            if (p != null && p.getId() != null) {
                salesMap.put(p.getId(), salesMap.getOrDefault(p.getId(), 0) + p.getSales());
            }
        }

        PriorityQueue<Product> minHeap = new PriorityQueue<>();

        for (Map.Entry<String, Integer> entry : salesMap.entrySet()) {
            Product product = new Product(entry.getKey(), entry.getValue());

            if (minHeap.size() < k) {
                minHeap.offer(product);
            } else if (product.compareTo(minHeap.peek()) > 0) {
                minHeap.poll();
                minHeap.offer(product);
            }
        }

        List<Product> result = new ArrayList<>(minHeap);
        result.sort((p1, p2) -> {
            if (p1.getSales() != p2.getSales()) {
                return Integer.compare(p2.getSales(), p1.getSales());
            }
            return p1.getId().compareTo(p2.getId());
        });

        return result;
    }

    public static void main(String[] args) {
        List<Product> inputList = List.of(
            new Product("A", 100),
            new Product("B", 200),
            new Product("A", 50),
            new Product("C", 200),
            new Product("D", 150),
            new Product("B", 50)
        );

        int k = 3;
        List<Product> topK = getTopKProducts(inputList, k);

        System.out.println("Top " + k + " Products: " + topK);
    }
}