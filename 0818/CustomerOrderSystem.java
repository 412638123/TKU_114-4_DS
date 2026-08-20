class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class OrderItem {
    private String itemName;
    private double price;
    private int quantity;

    public OrderItem(String itemName, double price, int quantity) {
        this.itemName = itemName;
        this.price = price < 0 ? 0 : price;
        this.quantity = quantity < 0 ? 0 : quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return price * quantity;
    }
}

class CustomerOrder {
    private String orderId;
    private Customer customer;
    private OrderItem[] items;

    public CustomerOrder(String orderId, Customer customer, OrderItem[] items) {
        this.orderId = orderId;
        this.customer = customer;
        if (items == null) {
            this.items = new OrderItem[0];
        } else {
            this.items = items.clone();
        }
    }

    public double getTotalAmount() {
        double total = 0;
        for (OrderItem item : items) {
            if (item != null) {
                total += item.getSubtotal();
            }
        }
        return total;
    }

    public int getTotalItemQuantity() {
        int totalQty = 0;
        for (OrderItem item : items) {
            if (item != null) {
                totalQty += item.getQuantity();
            }
        }
        return totalQty;
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("訂單編號：").append(orderId).append("\n");
        sb.append("顧客資訊：").append(customer.getName()).append(" (ID: ").append(customer.getCustomerId()).append(")\n");
        sb.append("=== 訂單品項 ===\n");
        for (OrderItem item : items) {
            if (item != null) {
                sb.append("- ").append(item.getItemName())
                  .append(" | 單價：").append(item.getPrice())
                  .append(" | 數量：").append(item.getQuantity())
                  .append(" | 小計：").append(item.getSubtotal()).append("\n");
            }
        }
        sb.append("----------------\n");
        sb.append("總品項數量：").append(getTotalItemQuantity()).append(" 件\n");
        sb.append("訂單總金額：").append(getTotalAmount()).append(" 元");
        return sb.toString();
    }
}

public class CustomerOrderSystem {
    public static void main(String[] args) {
        Customer customer = new Customer("C001", "陳小華");

        OrderItem[] items = {
            new OrderItem("無線滑鼠", 650.0, 2),
            new OrderItem("機械鍵盤", 2400.0, 1),
            new OrderItem("螢幕架", 800.0, 3)
        };

        CustomerOrder order = new CustomerOrder("ORD-2026-001", customer, items);

        System.out.println(order.getSummary());
    }
}