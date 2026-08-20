import java.util.Arrays;

class Transaction {
    private int sequence;
    private String type;
    private double amount;

    public Transaction(int sequence, String type, double amount) {
        this.sequence = sequence;
        this.type = type;
        this.amount = amount;
    }

    public int getSequence() {
        return sequence;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "交易序號 #" + sequence + " | 類型：" + type + " | 金額：" + amount;
    }
}

class Wallet {
    private String walletId;
    private String owner;
    private double balance;
    private Transaction[] history;
    private int transactionCount;
    private static int globalSequence = 1;

    public Wallet(String walletId, String owner, double initialBalance, int maxTransactions) {
        this.walletId = walletId;
        this.owner = owner;
        this.balance = initialBalance < 0 ? 0 : initialBalance;
        this.history = new Transaction[maxTransactions <= 0 ? 10 : maxTransactions];
        this.transactionCount = 0;
    }

    public boolean deposit(double amount) {
        if (amount <= 0 || transactionCount >= history.length) {
            return false;
        }
        this.balance += amount;
        history[transactionCount++] = new Transaction(globalSequence++, "DEPOSIT", amount);
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance || transactionCount >= history.length) {
            return false;
        }
        this.balance -= amount;
        history[transactionCount++] = new Transaction(globalSequence++, "WITHDRAW", amount);
        return true;
    }

    public boolean transferTo(Wallet target, double amount) {
        if (target == null || target == this || amount <= 0 || amount > this.balance) {
            return false;
        }
        if (this.transactionCount >= this.history.length || target.transactionCount >= target.history.length) {
            return false;
        }

        this.balance -= amount;
        target.balance += amount;

        this.history[this.transactionCount++] = new Transaction(globalSequence, "TRANSFER_OUT", amount);
        target.history[target.transactionCount++] = new Transaction(globalSequence, "TRANSFER_IN", amount);
        globalSequence++;

        return true;
    }

    public Transaction findTransaction(int sequence) {
        for (int i = 0; i < transactionCount; i++) {
            if (history[i].getSequence() == sequence) {
                return history[i];
            }
        }
        return null;
    }

    public double totalByType(String type) {
        if (type == null) return 0;
        double total = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (type.equalsIgnoreCase(history[i].getType())) {
                total += history[i].getAmount();
            }
        }
        return total;
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

    public void printStatement() {
        System.out.println("========================================");
        System.out.println("錢包對帳單 - ID: " + walletId + " | 持有者: " + owner);
        System.out.println("當前餘額: " + balance + " | 交易筆數: " + transactionCount + "/" + history.length);
        System.out.println("----------------------------------------");
        if (transactionCount == 0) {
            System.out.println("(無交易紀錄)");
        } else {
            for (int i = 0; i < transactionCount; i++) {
                System.out.println(history[i]);
            }
        }
        System.out.println("========================================\n");
    }
}

public class WalletHistoryManager {
    public static void main(String[] args) {
        Wallet walletA = new Wallet("W001", "張小明", 1000, 5);
        Wallet walletB = new Wallet("W002", "李小華", 500, 5);

        walletA.deposit(500);
        walletA.withdraw(200);
        walletA.transferTo(walletB, 400);
        walletB.withdraw(100);

        walletA.printStatement();
        walletB.printStatement();

        System.out.println("【搜尋交易測試】");
        Transaction t1 = walletA.findTransaction(3);
        System.out.println("在 WalletA 尋找序號 #3: " + (t1 != null ? t1 : "未找到"));

        Transaction t2 = walletA.findTransaction(99);
        System.out.println("在 WalletA 尋找序號 #99: " + (t2 != null ? t2 : "未找到"));

        System.out.println("\n【統計特定類型交易金額】");
        System.out.println("WalletA 總儲值金額 (DEPOSIT): " + walletA.totalByType("DEPOSIT"));
        System.out.println("WalletA 總轉出金額 (TRANSFER_OUT): " + walletA.totalByType("TRANSFER_OUT"));

        System.out.println("\n【測試交易陣列滿額時的防呆】");
        Wallet smallWallet = new Wallet("W003", "測試員", 100, 1);
        smallWallet.deposit(50);
        boolean overflowResult = smallWallet.deposit(50);
        System.out.println("陣列已滿時再存入結果: " + (overflowResult ? "成功" : "失敗"));
        System.out.println("smallWallet 最終餘額: " + smallWallet.getBalance());
    }
}