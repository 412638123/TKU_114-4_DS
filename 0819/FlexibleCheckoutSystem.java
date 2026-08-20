import java.util.ArrayList;
import java.util.List;

interface PricingPolicy {
    double calculateFinalPrice(double originalPrice);
}

class RegularPricing implements PricingPolicy {
    @Override
    public double calculateFinalPrice(double originalPrice) {
        return originalPrice < 0 ? 0 : originalPrice;
    }
}

class VipPricing implements PricingPolicy {
    @Override
    public double calculateFinalPrice(double originalPrice) {
        if (originalPrice < 0) return 0;
        return originalPrice * 0.85;
    }
}

class ThresholdDiscountPricing implements PricingPolicy {
    @Override
    public double calculateFinalPrice(double originalPrice) {
        if (originalPrice < 0) return 0;
        if (originalPrice >= 2000) {
            return originalPrice - 300;
        }
        return originalPrice;
    }
}

interface NotificationChannel {
    boolean notifyUser(String recipient, String message);
}

class EmailNotificationChannel implements NotificationChannel {
    @Override
    public boolean notifyUser(String recipient, String message) {
        if (recipient == null || recipient.trim().isEmpty()) return false;
        System.out.println("[Email 發送] 致 " + recipient + "：「" + message + "」");
        return true;
    }
}

class SmsNotificationChannel implements NotificationChannel {
    @Override
    public boolean notifyUser(String recipient, String message) {
        if (recipient == null || recipient.trim().isEmpty()) return false;
        System.out.println("[SMS 簡訊] 致 " + recipient + "：「" + message + "」");
        return true;
    }
}

class ConsoleNotificationChannel implements NotificationChannel {
    @Override
    public boolean notifyUser(String recipient, String message) {
        if (recipient == null || recipient.trim().isEmpty()) return false;
        System.out.println("[Console 系統通知]致 " + recipient + "：「" + message + "」");
        return true;
    }
}


class CheckoutResult {
    private String orderId;
    private double originalPrice;
    private double finalPrice;
    private boolean notificationSuccess;

    public CheckoutResult(String orderId, double originalPrice, double finalPrice, boolean notificationSuccess) {
        this.orderId = orderId;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.notificationSuccess = notificationSuccess;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public boolean isNotificationSuccess() {
        return notificationSuccess;
    }

    @Override
    public String toString() {
        return "訂單編號：" + orderId +
               " | 原價：" + originalPrice + " 元" +
               " | 折扣後金額：" + finalPrice + " 元" +
               " | 通知狀態：" + (notificationSuccess ? "發送成功" : "發送失敗");
    }
}


class CheckoutService {
    public CheckoutResult checkout(String orderId, double originalPrice, String recipient, PricingPolicy pricingPolicy, NotificationChannel notificationChannel) {
        double validPrice = originalPrice < 0 ? 0 : originalPrice;

        double finalPrice = (pricingPolicy != null) ? pricingPolicy.calculateFinalPrice(validPrice) : validPrice;
        
        String message = "您的訂單 " + orderId + " 已完成結帳，消費金額：" + finalPrice + " 元。";
        boolean notifyStatus = (notificationChannel != null) && notificationChannel.notifyUser(recipient, message);

        return new CheckoutResult(orderId, validPrice, finalPrice, notifyStatus);
    }
}


public class FlexibleCheckoutSystem {
    public static void main(String[] args) {
        CheckoutService checkoutService = new CheckoutService();

        PricingPolicy regular = new RegularPricing();
        PricingPolicy vip = new VipPricing();
        PricingPolicy threshold = new ThresholdDiscountPricing();

    
        NotificationChannel email = new EmailNotificationChannel();
        NotificationChannel sms = new SmsNotificationChannel();
        NotificationChannel console = new ConsoleNotificationChannel();

        List<CheckoutResult> results = new ArrayList<>();

        System.out.println("=== 開始執行 6 種組合測試 ===\n");

        System.out.println("--- [組合 1：原價策略 + Email 通知] ---");
        results.add(checkoutService.checkout("ORD-101", 1500, "user1@example.com", regular, email));

        System.out.println("\n--- [組合 2：原價策略 + SMS 簡訊] ---");
        results.add(checkoutService.checkout("ORD-102", 800, "0912345678", regular, sms));

        System.out.println("\n--- [組合 3：VIP 八五折 + Email 通知] ---");
        results.add(checkoutService.checkout("ORD-103", 2000, "vip_user@example.com", vip, email));

        System.out.println("\n--- [組合 4：VIP 八五折 + Console 通知] ---");
        results.add(checkoutService.checkout("ORD-104", 5000, "SystemUser", vip, console));

        System.out.println("\n--- [組合 5：滿 2000 折 300 + SMS 簡訊] ---");
        results.add(checkoutService.checkout("ORD-105", 2500, "0987654321", threshold, sms));

        System.out.println("\n--- [組合 6：滿 2000 折 300 (未達門檻) + Console 通知] ---");
        results.add(checkoutService.checkout("ORD-106", 1800, "Admin", threshold, console));

        
        System.out.println("\n========================================================");
        System.out.println("【所有結帳測試結果總覽】");
        System.out.println("========================================================");
        for (CheckoutResult result : results) {
            System.out.println(result);
        }
    }
}