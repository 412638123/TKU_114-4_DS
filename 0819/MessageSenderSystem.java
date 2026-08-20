interface MessageSender {
    boolean send(String receiver, String message);
}

class EmailSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.trim().isEmpty() || message == null || message.trim().isEmpty()) {
            return false;
        }
        System.out.println("[Email] 寄送至 " + receiver + "： " + message);
        return true;
    }
}

class SmsSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.trim().isEmpty() || message == null || message.trim().isEmpty()) {
            return false;
        }
        System.out.println("[SMS] 簡訊至 " + receiver + "： " + message);
        return true;
    }
}

class ConsoleSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.trim().isEmpty() || message == null || message.trim().isEmpty()) {
            return false;
        }
        System.out.println("[Console] 輸出至 " + receiver + "： " + message);
        return true;
    }
}

public class MessageSenderSystem {
    public static boolean notify(MessageSender sender, String receiver, String message) {
        if (sender == null) {
            return false;
        }
        return sender.send(receiver, message);
    }

    public static void main(String[] args) {
        MessageSender emailSender = new EmailSender();
        MessageSender smsSender = new SmsSender();
        MessageSender consoleSender = new ConsoleSender();

        System.out.println("【正常發送測試】");
        notify(emailSender, "user@example.com", "您的驗證碼為 123456");
        notify(smsSender, "0912345678", "您的包裹已到達門市");
        notify(consoleSender, "SystemAdmin", "伺服器運作正常");

        System.out.println("\n【邊界測試：無效訊息或接收者】");
        boolean result1 = notify(emailSender, "", "測試訊息");
        System.out.println("空白接收者發送結果：" + (result1 ? "成功" : "失敗"));

        boolean result2 = notify(smsSender, "0912345678", "  ");
        System.out.println("空白內文發送結果：" + (result2 ? "成功" : "失敗"));

        boolean result3 = notify(consoleSender, null, "測試訊息");
        System.out.println("null 接收者發送結果：" + (result3 ? "成功" : "失敗"));
    }
}