import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class WebsiteLinkGraph {

    private Map<String, Set<String>> outgoingLinks;
    private Map<String, Set<String>> incomingLinks;

    public WebsiteLinkGraph() {
        outgoingLinks = new HashMap<>();
        incomingLinks = new HashMap<>();
    }

    public void addPage(String page) {
        if (page != null) {
            outgoingLinks.putIfAbsent(page, new HashSet<>());
            incomingLinks.putIfAbsent(page, new HashSet<>());
        }
    }

    public void addLink(String fromPage, String toPage) {
        if (fromPage == null || toPage == null || fromPage.equals(toPage)) {
            return;
        }
        addPage(fromPage);
        addPage(toPage);

        outgoingLinks.get(fromPage).add(toPage);
        incomingLinks.get(toPage).add(fromPage);
    }

    public List<String> getOutgoingLinks(String page) {
        if (!outgoingLinks.containsKey(page)) {
            return new ArrayList<>();
        }
        List<String> links = new ArrayList<>(outgoingLinks.get(page));
        Collections.sort(links);
        return links;
    }

    public int getIncomingCount(String page) {
        if (!incomingLinks.containsKey(page)) {
            return 0;
        }
        return incomingLinks.get(page).size();
    }

    public List<String> getPagesWithNoIncoming() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : incomingLinks.entrySet()) {
            if (entry.getValue().isEmpty()) {
                result.add(entry.getKey());
            }
        }
        Collections.sort(result);
        return result;
    }

    public List<String> getPagesWithNoOutgoing() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : outgoingLinks.entrySet()) {
            if (entry.getValue().isEmpty()) {
                result.add(entry.getKey());
            }
        }
        Collections.sort(result);
        return result;
    }

    public void printGraphReport() {
        System.out.println("=== Website Link Graph Report ===");
        Set<String> allPages = new TreeSet<>(outgoingLinks.keySet());

        for (String page : allPages) {
            System.out.println("Page: " + page);
            System.out.println("  Outgoing Links: " + getOutgoingLinks(page));
            System.out.println("  Incoming Count: " + getIncomingCount(page));
        }

        System.out.println("\nPages with No Incoming Links: " + getPagesWithNoIncoming());
        System.out.println("Pages with No Outgoing Links: " + getPagesWithNoOutgoing());
    }

    public static void main(String[] args) {
        WebsiteLinkGraph graph = new WebsiteLinkGraph();

        graph.addLink("Home", "About");
        graph.addLink("Home", "Products");
        graph.addLink("Products", "ProductA");
        graph.addLink("Products", "ProductB");
        graph.addLink("About", "Contact");
        graph.addPage("OrphanPage");

        graph.printGraphReport();
    }
}