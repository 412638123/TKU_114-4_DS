class ExpressionNode {
    String val;
    ExpressionNode left;
    ExpressionNode right;

    public ExpressionNode(String val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }

    public ExpressionNode(String val, ExpressionNode left, ExpressionNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public boolean isOperator() {
        return left != null || right != null;
    }
}

public class TraversalSelector {

    public static void printPreorder(ExpressionNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val + " ");
        printPreorder(root.left);
        printPreorder(root.right);
    }

    public static void printInorder(ExpressionNode root) {
        if (root == null) {
            return;
        }
        boolean isOp = root.isOperator();
        if (isOp) {
            System.out.print("(");
        }
        printInorder(root.left);
        System.out.print(root.val);
        printInorder(root.right);
        if (isOp) {
            System.out.print(")");
        }
    }

    public static void printPostorder(ExpressionNode root) {
        if (root == null) {
            return;
        }
        printPostorder(root.left);
        printPostorder(root.right);
        System.out.print(root.val + " ");
    }

    public static void main(String[] args) {
        ExpressionNode a = new ExpressionNode("A");
        ExpressionNode b = new ExpressionNode("B");
        ExpressionNode c = new ExpressionNode("C");
        ExpressionNode d = new ExpressionNode("D");

        ExpressionNode multiply = new ExpressionNode("*", b, c);
        ExpressionNode add = new ExpressionNode("+", a, multiply);
        ExpressionNode root = new ExpressionNode("-", add, d);

        System.out.println("Expression Tree Built for: ((A + (B * C)) - D)\n");

        System.out.print("Prefix (Preorder)  : ");
        printPreorder(root);
        System.out.println();

        System.out.print("Infix (Inorder)    : ");
        printInorder(root);
        System.out.println();

        System.out.print("Postfix (Postorder): ");
        printPostorder(root);
        System.out.println();
    }
}