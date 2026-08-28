import java.util.ArrayList;
import java.util.List;

class Order {
    int orderId;
    String customer;
    int amount;
    String status;

    public Order(int orderId, String customer, int amount, String status) {
        this.orderId = orderId;
        this.customer = customer;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public String toString() {
        return "[Order #" + orderId + " | " + customer + " | $" + amount + " | Status:" + status + "]";
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

public class OrderManagementBst {

    public static boolean add(TreeNode[] rootWrapper, Order order) {
        if (order == null || order.amount < 0 || order.status == null || order.status.trim().isEmpty()) {
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

    public static Order find(TreeNode root, int orderId) {
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

    public static boolean updateStatus(TreeNode root, int orderId, String newStatus) {
        if (newStatus == null || newStatus.trim().isEmpty()) {
            return false;
        }
        Order order = find(root, orderId);
        if (order == null) {
            return false;
        }
        order.status = newStatus.trim();
        return true;
    }

    public static boolean cancel(TreeNode root, int orderId) {
        return updateStatus(root, orderId, "CANCELLED");
    }

    public static boolean remove(TreeNode[] rootWrapper, int orderId) {
        Order order = find(rootWrapper[0], orderId);
        if (order == null || !"CANCELLED".equalsIgnoreCase(order.status)) {
            return false;
        }
        rootWrapper[0] = removeHelper(rootWrapper[0], orderId);
        return true;
    }

    private static TreeNode removeHelper(TreeNode root, int orderId) {
        if (root == null) {
            return null;
        }

        if (orderId < root.order.orderId) {
            root.left = removeHelper(root.left, orderId);
        } else if (orderId > root.order.orderId) {
            root.right = removeHelper(root.right, orderId);
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
                root.right = removeHelper(root.right, successor.order.orderId);
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

    public static List<Order> idRangeReport(TreeNode root, int minOrderId, int maxOrderId) {
        List<Order> result = new ArrayList<>();
        if (minOrderId > maxOrderId) {
            return result;
        }
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

    public static int getTotalAmount(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return root.order.amount + getTotalAmount(root.left) + getTotalAmount(root.right);
    }

    public static void printReport(TreeNode root) {
        System.out.println("=== Order Management Inorder Report ===");
        if (root == null) {
            System.out.println("(No orders in system)");
        } else {
            inorderHelper(root);
        }
        System.out.println("Total System Amount: $" + getTotalAmount(root));
        System.out.println("---------------------------------------\n");
    }

    private static void inorderHelper(TreeNode node) {
        if (node == null) {
            return;
        }
        inorderHelper(node.left);
        System.out.println(node.order);
        inorderHelper(node.right);
    }

    public static void main(String[] args) {
        TreeNode[] rootWrapper = new TreeNode[1];

        System.out.println("=== 1. Add Order Tests ===");
        System.out.println("Add #101: " + add(rootWrapper, new Order(101, "Alice", 1500, "NEW")));
        System.out.println("Add #105: " + add(rootWrapper, new Order(105, "Bob", 2000, "PROCESSING")));
        System.out.println("Add #103: " + add(rootWrapper, new Order(103, "Charlie", 800, "COMPLETED")));
        System.out.println("Add #101 (Duplicate): " + add(rootWrapper, new Order(101, "David", 300, "NEW")));
        System.out.println("Add Negative Amount (-500): " + add(rootWrapper, new Order(102, "Eve", -500, "NEW")));
        System.out.println();

        printReport(rootWrapper[0]);

        System.out.println("=== 2. Update Status & Cancel Tests ===");
        System.out.println("Update #101 Status -> SHIPPED: " + updateStatus(rootWrapper[0], 101, "SHIPPED"));
        System.out.println("Cancel #105: " + cancel(rootWrapper[0], 105));
        System.out.println();

        System.out.println("=== 3. Remove Order Tests ===");
        System.out.println("Remove #101 (SHIPPED, should fail): " + remove(rootWrapper, 101));
        System.out.println("Remove #105 (CANCELLED, should pass): " + remove(rootWrapper, 105));
        System.out.println();

        System.out.println("=== 4. ID Range Report [#100 to #104] ===");
        List<Order> orders = idRangeReport(rootWrapper[0], 100, 104);
        for (Order o : orders) {
            System.out.println(o);
        }
        System.out.println();

        printReport(rootWrapper[0]);
    }
}