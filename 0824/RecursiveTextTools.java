public class RecursiveTextTools {

    public static String reverse(String str) {
        if (str == null || str.length() <= 1) {
            return str == null ? "" : str;
        }
        return str.charAt(str.length() - 1) + reverse(str.substring(0, str.length() - 1));
    }

    public static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }
        String cleaned = cleanString(str);
        return isPalindromeHelper(cleaned, 0, cleaned.length() - 1);
    }

    private static String cleanString(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isWhitespace(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    private static boolean isPalindromeHelper(String str, int left, int right) {
        if (left >= right) {
            return true;
        }
        if (str.charAt(left) != str.charAt(right)) {
            return false;
        }
        return isPalindromeHelper(str, left + 1, right - 1);
    }

    public static int countCharacter(String str, char target) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        int match = (str.charAt(0) == target) ? 1 : 0;
        return match + countCharacter(str.substring(1), target);
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Reverse Tests ===");
        System.out.println("reverse(\"\"): \"" + reverse("") + "\"");
        System.out.println("reverse(\"A\"): \"" + reverse("A") + "\"");
        System.out.println("reverse(\"Level\"): \"" + reverse("Level") + "\"");
        System.out.println("reverse(\"Hello World\"): \"" + reverse("Hello World") + "\"");

        System.out.println("\n=== 2. IsPalindrome Tests ===");
        System.out.println("isPalindrome(\"\"): " + isPalindrome(""));
        System.out.println("isPalindrome(\"A\"): " + isPalindrome("A"));
        System.out.println("isPalindrome(\"Level\"): " + isPalindrome("Level"));
        System.out.println("isPalindrome(\"A santa at nasa\"): " + isPalindrome("A santa at nasa"));
        System.out.println("isPalindrome(\"Java\"): " + isPalindrome("Java"));

        System.out.println("\n=== 3. CountCharacter Tests ===");
        System.out.println("countCharacter(\"\", 'a'): " + countCharacter("", 'a'));
        System.out.println("countCharacter(\"A\", 'A'): " + countCharacter("A", 'A'));
        System.out.println("countCharacter(\"Level\", 'e'): " + countCharacter("Level", 'e'));
        System.out.println("countCharacter(\"banana\", 'a'): " + countCharacter("banana", 'a'));
    }
}