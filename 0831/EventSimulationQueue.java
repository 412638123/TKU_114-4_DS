import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class EventSimulationQueue {

    public static class Event implements Comparable<Event> {
        private int id;
        private int time;
        private String type;
        private int sequence;

        public Event(int id, int time, String type, int sequence) {
            this.id = id;
            this.time = time;
            this.type = type;
            this.sequence = sequence;
        }

        public int getId() {
            return id;
        }

        public int getTime() {
            return time;
        }

        public String getType() {
            return type;
        }

        public int getSequence() {
            return sequence;
        }

        @Override
        public int compareTo(Event other) {
            if (this.time != other.time) {
                return Integer.compare(this.time, other.time);
            }
            return Integer.compare(this.sequence, other.sequence);
        }

        @Override
        public String toString() {
            return "[Time=" + time + ", Seq=" + sequence + ", ID=" + id + ", Type=" + type + "]";
        }
    }

    private PriorityQueue<Event> eventQueue;
    private Set<Integer> cancelledIds;
    private List<String> executionLog;

    public EventSimulationQueue() {
        eventQueue = new PriorityQueue<>();
        cancelledIds = new HashSet<>();
        executionLog = new ArrayList<>();
    }

    public void addEvent(Event event) {
        eventQueue.offer(event);
    }

    public void cancelEvent(int eventId) {
        cancelledIds.add(eventId);
    }

    public List<String> runSimulation() {
        while (!eventQueue.isEmpty()) {
            Event current = eventQueue.poll();
            if (cancelledIds.contains(current.getId())) {
                executionLog.add("Cancelled: " + current);
            } else {
                executionLog.add("Executed: " + current);
            }
        }
        return executionLog;
    }

    public static void main(String[] args) {
        EventSimulationQueue simulator = new EventSimulationQueue();

        simulator.addEvent(new Event(1, 10, "START", 1));
        simulator.addEvent(new Event(2, 5, "INIT", 1));
        simulator.addEvent(new Event(3, 10, "CHECK", 2));
        simulator.addEvent(new Event(4, 5, "LOG", 2));
        simulator.addEvent(new Event(5, 15, "END", 1));

        simulator.cancelEvent(3);

        List<String> logs = simulator.runSimulation();
        for (String log : logs) {
            System.out.println(log);
        }
    }
}