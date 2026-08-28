class Product {
    int id;
    String name;
    int stock;

    public Product(int id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "[ID:" + id + " | " + name + " | Stock:" + stock + "]";
    }
}

class TreeNode {
    Product product;
    TreeNode left;
    TreeNode right;

    public TreeNode(Product product) {
        this.product = product;
        this.left = null;
        this.right = null;
    }
}

public class ProductInventoryBst {

    public static boolean addProduct(TreeNode[] rootWrapper, Product product) {
        if (product == null) {
            return false;
        }
        if (rootWrapper[0] == null) {
            rootWrapper[0] = new TreeNode(product);
            return true;
        }

        TreeNode current = rootWrapper[0];
        TreeNode parent = null;

        while (current != null) {
            parent = current;
            if (product.id == current.product.id) {
                return false;
            } else if (product.id < current.product.id) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (product.id < parent.product.id) {
            parent.left = new TreeNode(product);
        } else {
            parent.right = new TreeNode(product);
        }
        return true;
    }

    public static Product findProduct(TreeNode root, int id) {
        TreeNode current = root;
        while (current != null) {
            if (id == current.product.id) {
                return current.product;
            } else if (id < current.product.id) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public static boolean restock(TreeNode root, int id, int amount) {
        if (amount <= 0) {
            return false;
        }
        Product p = findProduct(root, id);
        if (p == null) {
            return false;
        }
        p.stock += amount;
        return true;
    }

    public static boolean reduceStock(TreeNode root, int id, int amount) {
        if (amount <= 0) {
            return false;
        }
        Product p = findProduct(root, id);
        if (p == null || p.stock < amount) {
            return false;
        }
        p.stock -= amount;
        return true;
    }

    public static boolean deleteProduct(TreeNode[] rootWrapper, int id) {
        if (rootWrapper[0] == null || findProduct(rootWrapper[0], id) == null) {
            return false;
        }
        rootWrapper[0] = deleteHelper(rootWrapper[0], id);
        return true;
    }

    private static TreeNode deleteHelper(TreeNode root, int id) {
        if (root == null) {
            return null;
        }

        if (id < root.product.id) {
            root.left = deleteHelper(root.left, id);
        } else if (id > root.product.id) {
            root.right = deleteHelper(root.right, id);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                TreeNode successor = findMin(root.right);
                root.product = successor.product;
                root.right = deleteHelper(root.right, successor.product.id);
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

    public static void printInorderReport(TreeNode root) {
        System.out.println("=== Inventory Inorder Report ===");
        if (root == null) {
            System.out.println("(Inventory is empty)");
        } else {
            inorderHelper(root);
            System.out.println();
        }
        System.out.println("--------------------------------");
    }

    private static void inorderHelper(TreeNode node) {
        if (node == null) {
            return;
        }
        inorderHelper(node.left);
        System.out.println(node.product);
        inorderHelper(node.right);
    }

    public static void main(String[] args) {
        TreeNode[] rootWrapper = new TreeNode[1];

        System.out.println("=== 1. Add Product Tests ===");
        System.out.println("Add 101 (Keyboard): " + addProduct(rootWrapper, new Product(101, "Keyboard", 50)));
        System.out.println("Add 105 (Mouse): " + addProduct(rootWrapper, new Product(105, "Mouse", 100)));
        System.out.println("Add 102 (Monitor): " + addProduct(rootWrapper, new Product(102, "Monitor", 20)));
        System.out.println("Add 101 (Duplicate): " + addProduct(rootWrapper, new Product(101, "Keyboard Pro", 30)));

        printInorderReport(rootWrapper[0]);

        System.out.println("=== 2. Find Product Tests ===");
        System.out.println("Find 102: " + findProduct(rootWrapper[0], 102));
        System.out.println("Find 999: " + findProduct(rootWrapper[0], 999));
        System.out.println();

        System.out.println("=== 3. Restock & Reduce Stock Tests ===");
        System.out.println("Restock 102 (+30): " + restock(rootWrapper[0], 102, 30));
        System.out.println("Reduce 105 (-40): " + reduceStock(rootWrapper[0], 105, 40));
        System.out.println("Reduce 105 (-100, Exceeds): " + reduceStock(rootWrapper[0], 105, 100));

        printInorderReport(rootWrapper[0]);

        System.out.println("=== 4. Delete Product Tests ===");
        System.out.println("Delete 102: " + deleteProduct(rootWrapper, 102));
        System.out.println("Delete 999 (Non-existing): " + deleteProduct(rootWrapper, 999));

        printInorderReport(rootWrapper[0]);
    }
}