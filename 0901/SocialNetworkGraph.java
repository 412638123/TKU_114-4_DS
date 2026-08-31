import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SocialNetworkGraph {

    private Map<String, Set<String>> adjList;

    public SocialNetworkGraph() {
        adjList = new HashMap<>();
    }

    public void addUser(String user) {
        if (user != null) {
            adjList.putIfAbsent(user, new HashSet<>());
        }
    }

    public boolean addFriendship(String user1, String user2) {
        if (user1 == null || user2 == null || user1.equals(user2)) {
            return false;
        }
        addUser(user1);
        addUser(user2);

        boolean added1 = adjList.get(user1).add(user2);
        boolean added2 = adjList.get(user2).add(user1);

        return added1 && added2;
    }

    public boolean removeFriendship(String user1, String user2) {
        if (user1 == null || user2 == null || !adjList.containsKey(user1) || !adjList.containsKey(user2)) {
            return false;
        }
        boolean removed1 = adjList.get(user1).remove(user2);
        boolean removed2 = adjList.get(user2).remove(user1);

        return removed1 && removed2;
    }

    public List<String> getMutualFriends(String user1, String user2) {
        if (user1 == null || user2 == null || !adjList.containsKey(user1) || !adjList.containsKey(user2)) {
            return new ArrayList<>();
        }
        Set<String> set1 = adjList.get(user1);
        Set<String> set2 = adjList.get(user2);

        Set<String> mutual = new HashSet<>(set1);
        mutual.retainAll(set2);

        List<String> result = new ArrayList<>(mutual);
        Collections.sort(result);
        return result;
    }

    public List<String> getIsolatedUsers() {
        List<String> isolated = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : adjList.entrySet()) {
            if (entry.getValue().isEmpty()) {
                isolated.add(entry.getKey());
            }
        }
        Collections.sort(isolated);
        return isolated;
    }

    public List<String> getFriends(String user) {
        if (!adjList.containsKey(user)) {
            return new ArrayList<>();
        }
        List<String> friends = new ArrayList<>(adjList.get(user));
        Collections.sort(friends);
        return friends;
    }

    public static void main(String[] args) {
        SocialNetworkGraph social = new SocialNetworkGraph();

        social.addUser("Alice");
        social.addUser("Bob");
        social.addUser("Charlie");
        social.addUser("David");
        social.addUser("Eve");

        social.addFriendship("Alice", "Bob");
        social.addFriendship("Alice", "Charlie");
        social.addFriendship("Bob", "Charlie");
        social.addFriendship("David", "Charlie");

        System.out.println("Alice's Friends: " + social.getFriends("Alice"));
        System.out.println("Bob's Friends: " + social.getFriends("Bob"));
        System.out.println("Mutual Friends of Alice and Bob: " + social.getMutualFriends("Alice", "Bob"));

        System.out.println("Isolated Users before removal: " + social.getIsolatedUsers());

        System.out.println("\nRemoving friendship between Alice and Bob...");
        social.removeFriendship("Alice", "Bob");
        System.out.println("Alice's Friends: " + social.getFriends("Alice"));

        System.out.println("Isolated Users (Eve is isolated): " + social.getIsolatedUsers());
    }
}