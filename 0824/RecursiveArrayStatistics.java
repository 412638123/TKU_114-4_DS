public class RecursiveArrayStatistics {

    public static int maximum(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }
        return maximumHelper(arr, 0);
    }

    private static int maximumHelper(int[] arr, int index) {
        if (index == arr.length - 1) {
            return arr[index];
        }
        int maxOfRest = maximumHelper(arr, index + 1);
        return Math.max(arr[index], maxOfRest);
    }

    public static int minimum(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }
        return minimumHelper(arr, 0);
    }

    private static int minimumHelper(int[] arr, int index) {
        if (index == arr.length - 1) {
            return arr[index];
        }
        int minOfRest = minimumHelper(arr, index + 1);
        return Math.min(arr[index], minOfRest);
    }

    public static int countAbove(int[] arr, int threshold) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }
        return countAboveHelper(arr, threshold, 0);
    }

    private static int countAboveHelper(int[] arr, int threshold, int index) {
        if (index == arr.length) {
            return 0;
        }
        int count = arr[index] > threshold ? 1 : 0;
        return count + countAboveHelper(arr, threshold, index + 1);
    }

    public static void main(String[] args) {
        System.out.println("=== 1. 正常陣列測試 ===");
        int[] numbers = {15, -3, 42, 8, 99, 23, 42};
        System.out.print("陣列內容: ");
        for (int n : numbers) {
            System.out.print(n + " ");
        }
        System.out.println();

        System.out.println("最大值 (maximum): " + maximum(numbers));
        System.out.println("最小值 (minimum): " + minimum(numbers));
        System.out.println("大於 20 的個數 (countAbove > 20): " + countAbove(numbers, 20));

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 2. 單一元素陣列測試 ===");
        int[] single = {7};
        System.out.println("最大值: " + maximum(single));
        System.out.println("最小值: " + minimum(single));
        System.out.println("大於 5 的個數: " + countAbove(single, 5));

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 3. 異常處理測試 (Null 與 Empty Array) ===");

        try {
            maximum(null);
        } catch (IllegalArgumentException e) {
            System.out.println("成功捕捉 Null Array 異常: " + e.getMessage());
        }

        try {
            minimum(new int[]{});
        } catch (IllegalArgumentException e) {
            System.out.println("成功捕捉 Empty Array 異常: " + e.getMessage());
        }
    }
}