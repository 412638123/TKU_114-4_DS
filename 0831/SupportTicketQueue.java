import java.util.PriorityQueue;

public class SupportTicketQueue {
    public static class Ticket implements Comparable<Ticket> {
        private int id;
        private int severity;
        private int createdOrder;

        public Ticket(int id, int severity, int createdOrder) {
            this.id = id;
            this.severity = severity;
            this.createdOrder = createdOrder;
        }

        public int getId() {
            return id;
        }

        public int getSeverity() {
            return severity;
        }

        public int getCreatedOrder() {
            return createdOrder;
        }

        @Override
        public int compareTo(Ticket other) {
            if (this.severity != other.severity) {
                return Integer.compare(other.severity, this.severity);
            }
            return Integer.compare(this.createdOrder, other.createdOrder);
        }
    }

    private PriorityQueue<Ticket> pq;

    public SupportTicketQueue() {
        pq = new PriorityQueue<>();
    }

    public void addTicket(Ticket ticket) {
        pq.offer(ticket);
    }

    public Ticket nextTicket() {
        return pq.poll();
    }

    public boolean isEmpty() {
        return pq.isEmpty();
    }

    public static void main(String[] args) {
        SupportTicketQueue queue = new SupportTicketQueue();

        queue.addTicket(new Ticket(1, 3, 1));
        queue.addTicket(new Ticket(2, 5, 2));
        queue.addTicket(new Ticket(3, 5, 3));
        queue.addTicket(new Ticket(4, 2, 4));
        queue.addTicket(new Ticket(5, 5, 1));

        while (!queue.isEmpty()) {
            Ticket t = queue.nextTicket();
            System.out.println(t.getId() + "|" + t.getSeverity() + "|" + t.getCreatedOrder());
        }
    }
}