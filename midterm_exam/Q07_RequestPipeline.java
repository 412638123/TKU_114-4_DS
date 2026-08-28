package midterm_exam;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Q07_RequestPipeline {

    public static boolean isBalanced(String text) {
        if (text == null) {
            return false;
        }
        if (text.isEmpty()) {
            return true;
        }

        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if ((ch == ')' && top != '(') ||
                    (ch == ']' && top != '[') ||
                    (ch == '}' && top != '{')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static List<String> process(String[] commands) {
        if (commands == null) {
            return new ArrayList<>();
        }

        Deque<String> normalQueue = new ArrayDeque<>();
        Deque<String> urgentQueue = new ArrayDeque<>();
        List<String> result = new ArrayList<>();

        for (String cmd : commands) {
            if (cmd == null) {
                continue;
            }
            String trimmed = cmd.trim();
            if (trimmed.isEmpty()) {
                continue;
            }

            if ("PROCESS".equals(trimmed)) {
                if (!urgentQueue.isEmpty()) {
                    result.add(urgentQueue.poll());
                } else if (!normalQueue.isEmpty()) {
                    result.add(normalQueue.poll());
                } else {
                    result.add("EMPTY");
                }
            } else if (trimmed.startsWith("NORMAL ")) {
                String id = trimmed.substring(7).trim();
                if (!id.isEmpty()) {
                    normalQueue.offer(id);
                }
            } else if (trimmed.startsWith("URGENT ")) {
                String id = trimmed.substring(7).trim();
                if (!id.isEmpty()) {
                    urgentQueue.offer(id);
                }
            }
        }

        return result;
    }
}