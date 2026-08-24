public class GenericArrayTools {

    public static <T> int countMatches(T[] data, T target) {
        if (data == null || data.length == 0) {
            return 0;
        }

        int count = 0;
        for (T item : data) {
            if (target == null) {
                if (item == null) {
                    count++;
                }
            } else {
                if (target.equals(item)) {
                    count++;
                }
            }
        }
        return count;
    }

    public static <T> T last(T[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        return data[data.length - 1];
    }

    public static <T> void swap(T[] data, int first, int second) {
        if (data == null || data.length == 0) {
            return;
        }
        if (first < 0 || first >= data.length || second < 0 || second >= data.length) {
            return;
        }

        T temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }

    public static void main(String[] args) {
        String[] strArray = {"Apple", "Banana", "Apple", null, "Cherry"};

        System.out.println("【countMatches 測試】");
        System.out.println("Apple 出現次數: " + countMatches(strArray, "Apple"));
        System.out.println("null 出現次數: " + countMatches(strArray, null));
        System.out.println("Durian 出現次數: " + countMatches(strArray, "Durian"));
        System.out.println("null 陣列測試: " + countMatches((String[]) null, "Apple"));

        System.out.println("\n----------------------------------------\n");

        System.out.println("【last 測試】");
        System.out.println("正常陣列最後元素: " + last(strArray));
        System.out.println("空陣列最後元素: " + last(new Integer[0]));
        System.out.println("null 陣列最後元素: " + last((Integer[]) null));

        System.out.println("\n----------------------------------------\n");

        System.out.println("【swap 測試】");
        Integer[] numArray = {10, 20, 30, 40};
        System.out.print("交換前: ");
        printArray(numArray);

        swap(numArray, 0, 2);
        System.out.print("交換 index 0 與 2 後: ");
        printArray(numArray);

        System.out.println("測試不合法 index (0, 10):");
        swap(numArray, 0, 10);
        System.out.print("陣列維持原樣: ");
        printArray(numArray);
    }

    private static <T> void printArray(T[] array) {
        if (array == null) {
            System.out.println("null");
            return;
        }
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + (i < array.length - 1 ? ", " : ""));
        }
        System.out.println("]");
    }
}