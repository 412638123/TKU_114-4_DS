package final_exam;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Q12_CampusDispatchSystem {

    public record Request(String id, String location, int priority, long sequence) {}

    private final Map<String, Set<String>> graph = new HashMap<>();
    private final Map<String, Request> requestIdMap = new HashMap<>();
    private final PriorityQueue<Request> pq = new PriorityQueue<>(
            Comparator.comparingInt(Request::priority)
                    .thenComparingLong(Request::sequence)
                    .thenComparing(Request::id, Comparator.nullsLast(Comparator.naturalOrder()))
    );

    public boolean addLocation(String location) {
        if (location == null || graph.containsKey(location)) {
            return false;
        }
        graph.put(location, new LinkedHashSet<>());
        return true;
    }

    public boolean addRoad(String first, String second) {
        if (first == null || second == null || first.equals(second)) {
            return false;
        }

        if (!graph.containsKey(first) || !graph.containsKey(second)) {
            return false;
        }

        Set<String> neighborsFirst = graph.get(first);
        Set<String> neighborsSecond = graph.get(second);

        if (neighborsFirst.contains(second)) {
            return false;
        }

        neighborsFirst.add(second);
        neighborsSecond.add(first);
        return true;
    }

    public boolean submit(Request request) {
        if (request == null || request.id() == null || request.location() == null) {
            return false;
        }

        if (!graph.containsKey(request.location())) {
            return false;
        }

        if (requestIdMap.containsKey(request.id())) {
            return false;
        }

        requestIdMap.put(request.id(), request);
        pq.add(request);
        return true;
    }

    public Request nextReachable(String serviceCenter) {
        if (serviceCenter == null || !graph.containsKey(serviceCenter) || pq.isEmpty()) {
            return null;
        }

        Set<String> reachableLocations = getReachableLocations(serviceCenter);
        List<Request> temp = new ArrayList<>();
        Request matchedRequest = null;

        while (!pq.isEmpty()) {
            Request current = pq.poll();
            if (reachableLocations.contains(current.location())) {
                matchedRequest = current;
                break;
            } else {
                temp.add(current);
            }
        }

        pq.addAll(temp);

        if (matchedRequest != null) {
            requestIdMap.remove(matchedRequest.id());
        }

        return matchedRequest;
    }

    public List<String> route(String start, String target) {
        if (start == null || target == null) {
            return Collections.emptyList();
        }

        if (!graph.containsKey(start) || !graph.containsKey(target)) {
            return Collections.emptyList();
        }

        if (start.equals(target)) {
            List<String> path = new ArrayList<>();
            path.add(start);
            return path;
        }

        Map<String, String> parentMap = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();

        parentMap.put(start, null);
        queue.add(start);

        boolean found = false;

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(target)) {
                found = true;
                break;
            }

            Set<String> neighbors = graph.get(current);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (neighbor != null && !parentMap.containsKey(neighbor)) {
                        parentMap.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }
        }

        if (!found) {
            return Collections.emptyList();
        }

        List<String> path = new ArrayList<>();
        String curr = target;
        while (curr != null) {
            path.add(curr);
            curr = parentMap.get(curr);
        }

        Collections.reverse(path);
        return path;
    }

    public int pendingCount() {
        return pq.size();
    }

    private Set<String> getReachableLocations(String start) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            Set<String> neighbors = graph.get(current);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (neighbor != null && !visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
        }

        return visited;
    }
}