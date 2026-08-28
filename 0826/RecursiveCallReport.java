public class RecursiveCallReport {

    public static int sum(int[] data, int index) {
        if (data == null || index >= data.length) {
            System.out.println("[Base Case] index: " + index + " -> Out of bounds / Empty. Return: 0");
            return 0;
        }

        int currentValue = data[index];
        System.out.println("[Call] Enter index: " + index + " | Current value: " + currentValue);

        int recursiveResult = sum(data, index + 1);
        int returnValue = currentValue + recursiveResult;

        System.out.println("[Return] index: " + index + " | Current: " + currentValue + " + SubSum: " + recursiveResult + " = Return: " + returnValue);
        return returnValue;
    }

    public static void test(String label, int[] data) {
        System.out.println("=== " + label + " ===");
        int total = sum(data, 0);
        System.out.println("Final Sum: " + total + "\n");
    }

    public static void main(String[] args) {
        test("1. Normal Array", new int[]{10, 20, 30, 40});
        test("2. Single Element Array", new int[]{100});
        test("3. Empty Array", new int[]{});
    }
}