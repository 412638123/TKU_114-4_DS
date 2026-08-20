interface DeliveryMethod {
    double calculateShippingFee(double orderAmount);
    String getEstimatedDeliveryTime();
}

class HomeDelivery implements DeliveryMethod {
    @Override
    public double calculateShippingFee(double orderAmount) {
        if (orderAmount >= 1000) {
            return 0;
        }
        return 100;
    }

    @Override
    public String getEstimatedDeliveryTime() {
        return "宅配到府：預計 1-2 個工作天內送達";
    }
}

class ConvenienceStoreDelivery implements DeliveryMethod {
    @Override
    public double calculateShippingFee(double orderAmount) {
        if (orderAmount >= 600) {
            return 0;
        }
        return 60;
    }

    @Override
    public String getEstimatedDeliveryTime() {
        return "超商取貨：預計 2-3 個工作天到達指定門市";
    }
}

class SelfPickup implements DeliveryMethod {
    @Override
    public double calculateShippingFee(double orderAmount) {
        return 0;
    }

    @Override
    public String getEstimatedDeliveryTime() {
        return "門市自取：下單後可於營業時間內至現場領取";
    }
}

class OrderService {
    private String orderId;
    private double orderAmount;
    private DeliveryMethod deliveryMethod;

    public OrderService(String orderId, double orderAmount, DeliveryMethod deliveryMethod) {
        this.orderId = orderId;
        this.orderAmount = orderAmount < 0 ? 0 : orderAmount;
        this.deliveryMethod = deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public double calculateShippingFee() {
        if (deliveryMethod == null) {
            return 0;
        }
        return deliveryMethod.calculateShippingFee(orderAmount);
    }

    public double calculateTotalAmount() {
        return orderAmount + calculateShippingFee();
    }

    public void printOrderSummary() {
        System.out.println("訂單編號：" + orderId);
        System.out.println("商品金額：" + orderAmount + " 元");
        if (deliveryMethod != null) {
            System.out.println("配送說明：" + deliveryMethod.getEstimatedDeliveryTime());
            System.out.println("運費：" + calculateShippingFee() + " 元");
        } else {
            System.out.println("配送說明：未指定配送方式");
        }
        System.out.println("結帳總金額：" + calculateTotalAmount() + " 元");
    }
}

public class DeliveryStrategySystem {
    public static void main(String[] args) {
        DeliveryMethod home = new HomeDelivery();
        DeliveryMethod store = new ConvenienceStoreDelivery();
        DeliveryMethod pickup = new SelfPickup();

        System.out.println("【測試一：滿額宅配免運】");
        OrderService order1 = new OrderService("ORD-001", 1200, home);
        order1.printOrderSummary();

        System.out.println("\n----------------------------------------\n");

        System.out.println("【測試二：未達門檻超商取貨】");
        OrderService order2 = new OrderService("ORD-002", 500, store);
        order2.printOrderSummary();

        System.out.println("\n----------------------------------------\n");

        System.out.println("【測試三：動態變更為門市自取】");
        order2.setDeliveryMethod(pickup);
        order2.printOrderSummary();
    }
}