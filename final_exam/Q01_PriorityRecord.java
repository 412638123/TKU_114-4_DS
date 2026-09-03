package final_exam;
import java.util.ArrayList;
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

    public static void main(String[] args) {
        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job("JobC", 2, 100));
        jobs.add(new Job("JobA", 1, 50));
        jobs.add(new Job("JobB", 1, 20));
        jobs.add(null);

        List<String> result = processOrder(jobs);
        System.out.println("Q01 處理順序: " + result);
    }
}