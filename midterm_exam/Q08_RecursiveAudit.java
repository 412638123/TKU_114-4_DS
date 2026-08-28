package midterm_exam;
public class Q08_RecursiveAudit {

    public static int sumValid(int[] data, int index) {
        if (data == null) {
            return 0;
        }
        int idx = index < 0 ? 0 : index;
        if (idx >= data.length) {
            return 0;
        }

        int current = data[idx];
        int val = (current >= 0 && current <= 100) ? current : 0;
        return val + sumValid(data, idx + 1);
    }

    public static int countOccurrences(int[] data, int index, int target) {
        if (data == null) {
            return 0;
        }
        int idx = index < 0 ? 0 : index;
        if (idx >= data.length) {
            return 0;
        }

        int count = (data[idx] == target) ? 1 : 0;
        return count + countOccurrences(data, idx + 1, target);
    }

    public static boolean isPalindrome(String text, int left, int right) {
        if (text == null) {
            return false;
        }
        if (left >= right) {
            return true;
        }

        char leftChar = Character.toLowerCase(text.charAt(left));
        char rightChar = Character.toLowerCase(text.charAt(right));

        if (leftChar != rightChar) {
            return false;
        }

        return isPalindrome(text, left + 1, right - 1);
    }
}