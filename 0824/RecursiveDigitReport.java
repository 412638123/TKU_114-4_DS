public class RecursiveDigitReport {

    public static int digitSum(int n) {
        if (n < 0) {
            return digitSum(-n);
        }
        if (n < 10) {
            return n;
        }
        return (n % 10) + digitSum(n / 10);
    }

    public static int digitCount(int n) {
        if (n < 0) {
            return digitCount(-n);
        }
        if (n < 10) {
            return 1;
        }
        return 1 + digitCount(n / 10);
    }

    public static int countDigit(int n, int target) {
        if (n < 0) {
            return countDigit(-n, target);
        }
        if (n < 10) {
            return (n == target) ? 1 : 0;
        }
        int match = ((n % 10) == target) ? 1 : 0;
        return match + countDigit(n / 10, target);
    }

    public static void test(int number, int targetDigit) {
        System.out.println("測試數值: " + number);
        System.out.println("  - 數位和 (digitSum)      : " + digitSum(number));
        System.out.println("  - 數位個數 (digitCount)  : " + digitCount(number));
        System.out.println("  - 數字 " + targetDigit + " 出現次數 (countDigit): " + countDigit(number, targetDigit));
        System.out.println("----------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== 遞迴數位統計測試 (RecursiveDigitReport) ===\n");

        test(50205, 0);
        test(0, 0);
        test(-731, 3);
    }
}