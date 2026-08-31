import java.util.HashSet;
import java.util.Set;

public class InterestSetComparison {

    public static Set<String> getUnion(Set<String> set1, Set<String> set2) {
        Set<String> result = new HashSet<>();
        if (set1 != null) result.addAll(set1);
        if (set2 != null) result.addAll(set2);
        return result;
    }

    public static Set<String> getIntersection(Set<String> set1, Set<String> set2) {
        Set<String> result = new HashSet<>();
        if (set1 != null && set2 != null) {
            result.addAll(set1);
            result.retainAll(set2);
        }
        return result;
    }

    public static Set<String> getFirstOnly(Set<String> set1, Set<String> set2) {
        Set<String> result = new HashSet<>();
        if (set1 != null) {
            result.addAll(set1);
            if (set2 != null) {
                result.removeAll(set2);
            }
        }
        return result;
    }

    public static Set<String> getSecondOnly(Set<String> set1, Set<String> set2) {
        return getFirstOnly(set2, set1);
    }

    public static void main(String[] args) {
        Set<String> aliceInterests = Set.of("Reading", "Gaming", "Cooking", "Music");
        Set<String> bobInterests = Set.of("Gaming", "Music", "Sports", "Travel");

        System.out.println("Alice: " + aliceInterests);
        System.out.println("Bob: " + bobInterests);

        System.out.println("\nUnion: " + getUnion(aliceInterests, bobInterests));
        System.out.println("Intersection: " + getIntersection(aliceInterests, bobInterests));
        System.out.println("Alice Only: " + getFirstOnly(aliceInterests, bobInterests));
        System.out.println("Bob Only: " + getSecondOnly(aliceInterests, bobInterests));

        System.out.println("\nOriginal Alice (Unchanged): " + aliceInterests);
        System.out.println("Original Bob (Unchanged): " + bobInterests);
    }
}