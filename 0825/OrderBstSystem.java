import java.util.ArrayList;
import java.util.List;

class Order {
    int orderId;
    String customerName;
    int amount;

    public Order(int orderId, String customerName, int amount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "[Order #" + orderId + " | " + customerName + " | $" + amount + "]";
    }
}

class TreeNode {
    Order order;
    TreeNode left;
    TreeNode right;

    public TreeNode(Order order) {
        this.order = order;
        this.left = null;
        this.right = null;
    }
}

public class OrderBstSystem {

    public static boolean addOrder(TreeNode[] rootWrapper, Order order) {
        if (order == null) {
            return false;
        }
        if (rootWrapper[0] == null) {
            rootWrapper[0] = new TreeNode(order);
            return true;
        }

        TreeNode current = rootWrapper[0];
        TreeNode parent = null;

        while (current != null) {
            parent = current;
            if (order.orderId == current.order.orderId) {
                return false;
            } else if (order.orderId < current.order.orderId) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (order.orderId < parent.order.orderId) {
            parent.left = new TreeNode(order);
        } else {
            parent.right = new TreeNode(order);
        }
        return true;
    }

    public static Order findOrder(TreeNode root, int orderId) {
        TreeNode current = root;
        while (current != null) {
            if (orderId == current.order.orderId) {
                return current.order;
            } else if (orderId < current.order.orderId) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public static boolean updateAmount(TreeNode root, int orderId, int newAmount) {
        Order order = findOrder(root, orderId);
        if (order == null || newAmount < 0) {
            return false;
        }
        order.amount = newAmount;
        return true;
    }

    public static boolean cancelOrder(TreeNode[] rootWrapper, int orderId) {
        if (rootWrapper[0] == null || findOrder(rootWrapper[0], orderId) == null) {
            return false;
        }
        rootWrapper[0] = cancelHelper(rootWrapper[0], orderId);
        return true;
    }

    private static TreeNode cancelHelper(TreeNode root, int orderId) {
        if (root == null) {
            return null;
        }

        if (orderId < root.order.orderId) {
            root.left = cancelHelper(root.left, orderId);
        } else if (orderId > root.order.orderId) {
            root.right = cancelHelper(root.right, orderId);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                TreeNode successor = findMin(root.right);
                root.order = successor.order;
                root.right = cancelHelper(root.right, successor.order.orderId);
            }
        }
        return root;
    }

    private static TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static List<Order> rangeReport(TreeNode root, int minOrderId, int maxOrderId) {
        List<Order> result = new ArrayList<>();
        rangeHelper(root, minOrderId, maxOrderId, result);
        return result;
    }

    private static void rangeHelper(TreeNode node, int minId, int maxId, List<Order> result) {
        if (node == null) {
            return;
        }
        if (node.order.orderId > minId) {
            rangeHelper(node.left, minId, maxId, result);
        }
        if (node.order.orderId >= minId && node.order.orderId <= maxId) {
            result.add(node.order);
        }
        if (node.order.orderId < maxId) {
            rangeHelper(node.right, minId, maxId, result);
        }
    }

    public static void printSummary(TreeNode root) {
        int count = countOrders(root);
        int totalAmount = sumAmount(root);
        System.out.println("=== Order System Summary ===");
        System.out.println("Total Active Orders: " + count);
        System.out.println("Total System Revenue: $" + totalAmount);
        System.out.println("----------------------------");
    }

    private static int countOrders(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + countOrders(root.left) + countOrders(root.right);
    }

    private static int sumAmount(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return root.order.amount + sumAmount(root.left) + sumAmount(root.right);
    }

    public static void main(String[] args) {
        TreeNode[] rootWrapper = new TreeNode[1];

        System.out.println("=== 1. Add Orders ===");
        System.out.println("Add #1001: " + addOrder(rootWrapper, new Order(1001, "Alice", 1500)));
        System.out.println("Add #1005: " + addOrder(rootWrapper, new Order(1005, "Bob", 2300)));
        System.out.println("Add #1002: " + addOrder(rootWrapper, new Order(1002, "Charlie", 800)));
        System.out.println("Add #1004: " + addOrder(rootWrapper, new Order(1004, "David", 3200)));
        System.out.println("Add #1001 (Duplicate): " + addOrder(rootWrapper, new Order(1001, "Eve", 500)));
        System.out.println();

        printSummary(rootWrapper[0]);

        System.out.println("=== 2. Find & Update Orders ===");
        System.out.println("Find #1002: " + findOrder(rootWrapper[0], 1002));
        System.out.println("Update #1002 Amount -> $1200: " + updateAmount(rootWrapper[0], 1002, 1200));
        System.out.println("Find #1002 After Update: " + findOrder(rootWrapper[0], 1002));
        System.out.println();

        System.out.println("=== 3. Range Report [#1001 to #1003] ===");
        List<Order> report = rangeReport(rootWrapper[0], 1001, 1003);
        for (Order o : report) {
            System.out.println(o);
        }
        System.out.println();

        System.out.println("=== 4. Cancel Order ===");
        System.out.println("Cancel #1002: " + cancelOrder(rootWrapper, 1002));
        System.out.println("Cancel #9999 (Non-existing): " + cancelOrder(rootWrapper, 9999));
        System.out.println();

        printSummary(rootWrapper[0]);
    }
}