import java.util.ArrayDeque;
import java.util.Deque;

public class TextEditorHistory {
    private final Deque<String> undoStack = new ArrayDeque<>();
    private final Deque<String> redoStack = new ArrayDeque<>();

    public void type(String text) {
        if (text == null || text.isEmpty()) {
            return;
        }
        undoStack.push(text);
        redoStack.clear();
        System.out.println("輸入文字：\"" + text + "\"");
        printState();
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("執行 Undo 失敗：已無可復原的操作");
            printState();
            return;
        }
        String text = undoStack.pop();
        redoStack.push(text);
        System.out.println("執行 Undo：復原了 \"" + text + "\"");
        printState();
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("執行 Redo 失敗：已無可重做的操作");
            printState();
            return;
        }
        String text = redoStack.pop();
        undoStack.push(text);
        System.out.println("執行 Redo：重做了 \"" + text + "\"");
        printState();
    }

    private void printState() {
        System.out.println("  [Undo Stack (Top -> Bottom)]: " + undoStack);
        System.out.println("  [Redo Stack (Top -> Bottom)]: " + redoStack);
        System.out.println("  ----------------------------------------");
    }

    public static void main(String[] args) {
        TextEditorHistory editor = new TextEditorHistory();

        System.out.println("=== 文字編輯器 Undo / Redo 功能測試 ===\n");

        System.out.println("1. 初始空 Stack 測試：");
        editor.undo();
        editor.redo();

        System.out.println("\n2. 連續輸入文字：");
        editor.type("Hello");
        editor.type("World");
        editor.type("Java");

        System.out.println("\n3. 連續執行 Undo：");
        editor.undo();
        editor.undo();

        System.out.println("\n4. 執行 Redo：");
        editor.redo();

        System.out.println("\n5. 新增操作 (驗證 Redo Stack 是否被清空)：");
        editor.type("Programming");

        System.out.println("\n6. 嘗試在清空後執行 Redo：");
        editor.redo();

        System.out.println("\n7. 一路 Undo 到空：");
        editor.undo();
        editor.undo();
        editor.undo();
        editor.undo();
    }
}