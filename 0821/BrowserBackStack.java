import java.util.ArrayDeque;
import java.util.Deque;

public class BrowserBackStack {
    private final Deque<String> history = new ArrayDeque<>();

    public void visit(String url) {
        if (url != null && !url.trim().isEmpty()) {
            history.push(url.trim());
            System.out.println("造訪頁面：" + url);
        }
    }

    public String back() {
        if (history.isEmpty()) {
            System.out.println("無法返回：瀏覽歷程為空");
            return null;
        }
        String leftUrl = history.pop();
        System.out.println("離開頁面：" + leftUrl);
        return leftUrl;
    }

    public String current() {
        if (history.isEmpty()) {
            System.out.println("當前頁面：(無)");
            return null;
        }
        return history.peek();
    }

    public static void main(String[] args) {
        BrowserBackStack browser = new BrowserBackStack();

        System.out.println("=== 開始瀏覽器返回功能測試 (包含邊界條件) ===\n");

        // 操作 1: 剛開啟瀏覽器時查看當前頁面
        System.out.println("[操作 1] 檢查初始當前頁面");
        System.out.println("當前網址：" + browser.current());

        System.out.println("\n----------------------------------------\n");

        // 操作 2: 空 stack 時嘗試返回
        System.out.println("[操作 2] 空歷程時執行 back()");
        browser.back();

        System.out.println("\n----------------------------------------\n");

        // 操作 3: 造訪第一個網頁
        System.out.println("[操作 3] 造訪 Google");
        browser.visit("https://www.google.com");
        System.out.println("當前網址：" + browser.current());

        System.out.println("\n----------------------------------------\n");

        // 操作 4: 造訪第二個網頁
        System.out.println("[操作 4] 造訪 GitHub");
        browser.visit("https://github.com");
        System.out.println("當前網址：" + browser.current());

        System.out.println("\n----------------------------------------\n");

        // 操作 5: 造訪第三個網頁
        System.out.println("[操作 5] 造訪 ChatGPT");
        browser.visit("https://chatgpt.com");
        System.out.println("當前網址：" + browser.current());

        System.out.println("\n----------------------------------------\n");

        // 操作 6: 執行返回上一頁
        System.out.println("[操作 6] 執行 back()");
        browser.back();
        System.out.println("當前網址：" + browser.current());

        System.out.println("\n----------------------------------------\n");

        // 操作 7: 再次執行返回上一頁
        System.out.println("[操作 7] 再次執行 back()");
        browser.back();
        System.out.println("當前網址：" + browser.current());

        System.out.println("\n----------------------------------------\n");

        // 操作 8: 返回到最底層頁面後再次 back
        System.out.println("[操作 8] 返回最後一個頁面並繼續 back()");
        browser.back();
        System.out.println("當前網址：" + browser.current());
        browser.back();
    }
}