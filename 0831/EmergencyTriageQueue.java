import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class EmergencyTriageQueue {

    public static class Patient implements Comparable<Patient> {
        private String medicalRecordNumber;
        private int severity;
        private int arrivalOrder;

        public Patient(String medicalRecordNumber, int severity, int arrivalOrder) {
            this.medicalRecordNumber = medicalRecordNumber;
            this.severity = severity;
            this.arrivalOrder = arrivalOrder;
        }

        public String getMedicalRecordNumber() {
            return medicalRecordNumber;
        }

        public int getSeverity() {
            return severity;
        }

        public int getArrivalOrder() {
            return arrivalOrder;
        }

        @Override
        public int compareTo(Patient other) {
            if (this.severity != other.severity) {
                return Integer.compare(other.severity, this.severity);
            }
            if (this.arrivalOrder != other.arrivalOrder) {
                return Integer.compare(this.arrivalOrder, other.arrivalOrder);
            }
            return this.medicalRecordNumber.compareTo(other.medicalRecordNumber);
        }

        @Override
        public String toString() {
            return "Patient[ID=" + medicalRecordNumber + ", Severity=" + severity + ", Order=" + arrivalOrder + "]";
        }
    }

    private PriorityQueue<Patient> queue;
    private int orderCounter;

    public EmergencyTriageQueue() {
        queue = new PriorityQueue<>();
        orderCounter = 0;
    }

    public void register(String medicalRecordNumber, int severity) {
        orderCounter++;
        Patient p = new Patient(medicalRecordNumber, severity, orderCounter);
        queue.offer(p);
    }

    public Patient peekNext() {
        if (isEmpty()) {
            throw new NoSuchElementException("Triage queue is empty.");
        }
        return queue.peek();
    }

    public Patient callNext() {
        if (isEmpty()) {
            throw new NoSuchElementException("Triage queue is empty.");
        }
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        EmergencyTriageQueue triage = new EmergencyTriageQueue();

        triage.register("P001", 3);
        triage.register("P002", 5);
        triage.register("P003", 5);
        triage.register("P004", 1);

        System.out.println("Current patients count: " + triage.size());
        System.out.println("Next patient: " + triage.peekNext());

        while (!triage.isEmpty()) {
            Patient called = triage.callNext();
            System.out.println("Called: " + called);
        }

        try {
            triage.peekNext();
        } catch (NoSuchElementException e) {
            System.out.println("Error handled: " + e.getMessage());
        }

        try {
            triage.callNext();
        } catch (NoSuchElementException e) {
            System.out.println("Error handled: " + e.getMessage());
        }
    }
}