class DigitalWallet {
    private String walletId;
    private String owner;
    private double balance;
    private int transactionCount;

    public DigitalWallet(String walletId, String owner, double initialBalance) {
        this.walletId = walletId;
        this.owner = owner;
        this.balance = initialBalance < 0 ? 0 : initialBalance;
        this.transactionCount = 0;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        this.balance += amount;
        this.transactionCount++;
        return true;
    }

    public boolean pay(double amount) {
        if (amount <= 0 || amount > this.balance) {
            return false;
        }
        this.balance -= amount;
        this.transactionCount++;
        return true;
    }

    public boolean refund(double amount) {
        if (amount <= 0) {
            return false;
        }
        this.balance += amount;
        this.transactionCount++;
        return true;
    }

    public String getWalletId() {
        return walletId;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    @Override
    public String toString() {
        return "錢包 ID：" + walletId + " | 持有者：" + owner + " | 餘額：" + balance + " | 交易次數：" + transactionCount;
    }
}

public class DigitalWalletSystem {
    public static void main(String[] args) {
        DigitalWallet wallet = new DigitalWallet("W1001", "張小明", 1000.0);
        System.out.println("【初始狀態】\n" + wallet);

        System.out.println("\n【1. 測試正常儲值 500】");
        boolean d1 = wallet.deposit(500);
        System.out.println("結果：" + (d1 ? "成功" : "失敗") + " | " + wallet);

        System.out.println("\n【2. 測試正常付款 300】");
        boolean p1 = wallet.pay(300);
        System.out.println("結果：" + (p1 ? "成功" : "失敗") + " | " + wallet);

        System.out.println("\n【3. 測試餘額不足付款 2000】");
        boolean p2 = wallet.pay(2000);
        System.out.println("結果：" + (p2 ? "成功" : "失敗") + " | " + wallet);

        System.out.println("\n【4. 測試不合法金額（負數金額）】");
        boolean d2 = wallet.deposit(-100);
        System.out.println("負數儲值結果：" + (d2 ? "成功" : "失敗"));
        boolean p3 = wallet.pay(-50);
        System.out.println("負數付款結果：" + (p3 ? "成功" : "失敗") + " | " + wallet);

        System.out.println("\n【5. 測試退款 200】");
        boolean r1 = wallet.refund(200);
        System.out.println("結果：" + (r1 ? "成功" : "失敗") + " | " + wallet);
    }
}