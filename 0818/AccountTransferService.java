class Account {
    private String id;
    private String name;
    private int balance;

    public Account(String id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance < 0 ? 0 : balance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        return "帳戶 ID：" + id + " | 持有者：" + name + " | 餘額：" + balance;
    }
}

class TransferService {
    public boolean transfer(Account source, Account target, int amount) {
        if (source == null || target == null) {
            return false;
        }
        if (source == target) {
            return false;
        }
        if (amount <= 0) {
            return false;
        }
        if (source.getBalance() < amount) {
            return false;
        }

        source.withdraw(amount);
        target.deposit(amount);
        return true;
    }
}

public class AccountTransferService {
    public static void main(String[] args) {
        Account acc1 = new Account("A001", "張小明", 1000);
        Account acc2 = new Account("A002", "李小華", 500);
        TransferService service = new TransferService();

        System.out.println("【初始狀態】");
        System.out.println("acc1: " + acc1);
        System.out.println("acc2: " + acc2);

        System.out.println("\n【1. 測試成功轉帳 300 元】");
        boolean r1 = service.transfer(acc1, acc2, 300);
        System.out.println("結果：" + (r1 ? "成功" : "失敗"));
        System.out.println("acc1: " + acc1);
        System.out.println("acc2: " + acc2);

        System.out.println("\n【2. 測試餘額不足轉帳 2000 元】");
        boolean r2 = service.transfer(acc1, acc2, 2000);
        System.out.println("結果：" + (r2 ? "成功" : "失敗"));
        System.out.println("acc1: " + acc1);
        System.out.println("acc2: " + acc2);

        System.out.println("\n【3. 測試同帳戶轉帳】");
        boolean r3 = service.transfer(acc1, acc1, 100);
        System.out.println("結果：" + (r3 ? "成功" : "失敗"));
        System.out.println("acc1: " + acc1);

        System.out.println("\n【4. 測試 null 目標帳戶】");
        boolean r4 = service.transfer(acc1, null, 100);
        System.out.println("結果：" + (r4 ? "成功" : "失敗"));
        System.out.println("acc1: " + acc1);
    }
}