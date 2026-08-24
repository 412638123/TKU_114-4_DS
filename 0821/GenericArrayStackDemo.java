@SuppressWarnings("unchecked")
class ArrayStack<T> {
    private final T[] elements;
    private int top;
    private final int capacity;

    public ArrayStack(int capacity) {
        if (capacity <= 0) {
            capacity = 10;
        }
        this.capacity = capacity;
        this.elements = (T[]) new Object[capacity];
        this.top = -1;
    }

    public boolean push(T item) {
        if (isFull() || item == null) {
            return false;
        }
        elements[++top] = item;
        return true;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T item = elements[top];
        elements[top--] = null;
        return item;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return elements[top];
    }

    public int size() {
        return top + 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == capacity - 1;
    }
}

public class GenericArrayStackDemo {
    public static void main(String[] args) {
        System.out.println("=== 1. ArrayStack<String> 測試 (容量 3) ===");
        ArrayStack<String> stringStack = new ArrayStack<>(3);

        System.out.println("Push 'A': " + stringStack.push("A"));
        System.out.println("Push 'B': " + stringStack.push("B"));
        System.out.println("Push 'C': " + stringStack.push("C"));
        System.out.println("Push 'D' (已滿): " + stringStack.push("D"));

        System.out.println("isFull: " + stringStack.isFull());
        System.out.println("size: " + stringStack.size());
        System.out.println("peek: " + stringStack.peek());

        System.out.println("Pop: " + stringStack.pop());
        System.out.println("Pop: " + stringStack.pop());
        System.out.println("size: " + stringStack.size());

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 2. ArrayStack<Integer> 測試 (容量 2) ===");
        ArrayStack<Integer> intStack = new ArrayStack<>(2);

        System.out.println("isEmpty: " + intStack.isEmpty());
        System.out.println("Pop (空堆疊): " + intStack.pop());
        System.out.println("Peek (空堆疊): " + intStack.peek());

        System.out.println("Push 100: " + intStack.push(100));
        System.out.println("Push 200: " + intStack.push(200));

        System.out.println("Pop: " + intStack.pop());
        System.out.println("Pop: " + intStack.pop());
        System.out.println("isEmpty: " + intStack.isEmpty());
    }
}