package final_exam;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Q01_PriorityRecord {

    public record Job(String id, int priority, long sequence) {}

    public static List<String> processOrder(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            return Collections.emptyList();
        }

        return jobs.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(Job::priority)
                        .thenComparingLong(Job::sequence)
                        .thenComparing(Job::id, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(Job::id)
                .collect(Collectors.toList());
    }
}