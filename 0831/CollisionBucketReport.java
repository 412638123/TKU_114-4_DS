import java.util.ArrayList;
import java.util.List;

public class CollisionBucketReport {

    public static void generateReport(List<Integer> keys, int bucketCount) {
        if (bucketCount <= 0) {
            System.out.println("Invalid bucket count.");
            return;
        }

        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        if (keys != null) {
            for (Integer key : keys) {
                if (key == null) {
                    continue;
                }
                int bucketIndex = Math.floorMod(key, bucketCount);
                buckets.get(bucketIndex).add(key);
            }
        }

        int totalCollisions = 0;
        int maxChainLength = 0;

        for (int i = 0; i < bucketCount; i++) {
            List<Integer> bucket = buckets.get(i);
            int chainLength = bucket.size();

            if (chainLength > 1) {
                totalCollisions += (chainLength - 1);
            }
            if (chainLength > maxChainLength) {
                maxChainLength = chainLength;
            }

            System.out.println("Bucket " + i + ": " + bucket);
        }

        System.out.println("Total Collisions: " + totalCollisions);
        System.out.println("Max Chain Length: " + maxChainLength);
    }

    public static void main(String[] args) {
        List<Integer> keys = List.of(10, -5, 15, 20, 10, -15, 7);
        int bucketCount = 5;

        System.out.println("--- Test 1 ---");
        generateReport(keys, bucketCount);

        System.out.println("\n--- Test 2 (Empty Input) ---");
        generateReport(new ArrayList<>(), 3);
    }
}