import java.util.ArrayList;
import java.util.List;

public class StudentIdHashAnalysis {

    public static void analyze(List<String> studentIds, int bucketCount) {
        if (bucketCount <= 0) {
            System.out.println("Bucket count must be positive.");
            return;
        }

        int[] bucketSizes = new int[bucketCount];

        if (studentIds != null) {
            for (String id : studentIds) {
                if (id == null) {
                    continue;
                }
                int hash = Math.floorMod(id.hashCode(), bucketCount);
                bucketSizes[hash]++;
            }
        }

        int totalCollisions = 0;
        int maxChain = 0;
        int nonZeroBuckets = 0;
        int totalElements = 0;

        for (int count : bucketSizes) {
            totalElements += count;
            if (count > 1) {
                totalCollisions += (count - 1);
            }
            if (count > maxChain) {
                maxChain = count;
            }
            if (count > 0) {
                nonZeroBuckets++;
            }
        }

        double avgChainAll = (double) totalElements / bucketCount;
        double avgChainNonEmpty = nonZeroBuckets == 0 ? 0 : (double) totalElements / nonZeroBuckets;

        System.out.println("Bucket Count: " + bucketCount);
        System.out.println("Total Student IDs: " + totalElements);
        for (int i = 0; i < bucketCount; i++) {
            System.out.println("  Bucket " + i + ": " + bucketSizes[i] + " items");
        }
        System.out.println("Total Collisions: " + totalCollisions);
        System.out.println("Max Chain Length: " + maxChain);
        System.out.printf("Average Chain Length (All Buckets): %.2f\n", avgChainAll);
        System.out.printf("Average Chain Length (Non-Empty Buckets): %.2f\n", avgChainNonEmpty);
    }

    public static void main(String[] args) {
        List<String> studentIds = List.of(
            "110501001", "110501002", "110501003", "110501015",
            "110501027", "110502001", "110502012", "110502033",
            "110503008", "110503021", "110503045", "110504002"
        );

        int bucketCount1 = 7;
        int bucketCount2 = 13;

        System.out.println("========== Analysis 1 (Bucket Count = " + bucketCount1 + ") ==========");
        analyze(studentIds, bucketCount1);

        System.out.println("\n========== Analysis 2 (Bucket Count = " + bucketCount2 + ") ==========");
        analyze(studentIds, bucketCount2);

        System.out.println("\n========== Comparison Summary ==========");
        System.out.println("When changing bucket count from " + bucketCount1 + " to " + bucketCount2 + ":");
        System.out.println("- Increasing bucket count (especially using a prime number like 13) generally reduces collisions.");
        System.out.println("- Max chain length and overall collision rate decrease as elements distribute more evenly.");
    }
}