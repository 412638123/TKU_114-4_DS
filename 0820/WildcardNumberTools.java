import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WildcardNumberTools {

    public static double average(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Number num : values) {
            if (num != null) {
                sum += num.doubleValue();
            }
        }
        return sum / values.size();
    }

    public static double maximum(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return Double.NaN;
        }
        double max = Double.NEGATIVE_INFINITY;
        boolean hasNonNull = false;

        for (Number num : values) {
            if (num != null) {
                double val = num.doubleValue();
                if (val > max) {
                    max = val;
                }
                hasNonNull = true;
            }
        }
        return hasNonNull ? max : Double.NaN;
    }

    public static void addRange(List<? super Integer> target, int start, int end) {
        if (target == null || start > end) {
            return;
        }
        for (int i = start; i <= end; i++) {
            target.add(i);
        }
    }

    public static void main(String[] args) {
        List<Integer> intList = Arrays.asList(10, 20, 30, 40, 50);
        List<Double> doubleList = Arrays.asList(1.5, 3.5, 2.0, 8.0);
        List<Integer> emptyIntList = new ArrayList<>();

        System.out.println("=== 1. average 測試 ===");
        System.out.println("List<Integer> 平均: " + average(intList));
        System.out.println("List<Double> 平均: " + average(doubleList));
        System.out.println("空 List 平均: " + average(emptyIntList));
        System.out.println("null List 平均: " + average(null));

        System.out.println("\n=== 2. maximum 測試 ===");
        System.out.println("List<Integer> 最大值: " + maximum(intList));
        System.out.println("List<Double> 最大值: " + maximum(doubleList));
        System.out.println("空 List 最大值: " + maximum(emptyIntList));
        System.out.println("null List 最大值: " + maximum(null));

        System.out.println("\n=== 3. addRange 測試 ===");
        List<Number> numList = new ArrayList<>();
        addRange(numList, 1, 5);
        System.out.println("寫入 List<Number> (1 到 5): " + numList);

        List<Object> objList = new ArrayList<>();
        addRange(objList, 10, 12);
        System.out.println("寫入 List<Object> (10 到 12): " + objList);

        System.out.println("\n測試 start > end (5 到 1):");
        addRange(numList, 5, 1);
        System.out.println("結果未改變: " + numList);
    }
}